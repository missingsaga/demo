package com.loki.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loki.demo.entity.Role;
import com.loki.demo.entity.RolePermission;
import com.loki.demo.enums.ResultCode;
import com.loki.demo.enums.Status;
import com.loki.demo.exception.APIException;
import com.loki.demo.mapper.RoleMapper;
import com.loki.demo.service.RolePermissionService;
import com.loki.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 角色管理Service实现
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;

    private final RolePermissionService rolePermissionService;

    @Override
    public IPage<Role> page(String word, Pageable pageable) {
        Page<Role> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        return roleMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Cacheable(value = "roles", key = "#id", unless = "#result eq null")
    public Role info(Long id) {
        Role role = roleMapper.selectById(id);
        if (Objects.isNull(role)) {
            throw new APIException(ResultCode.FAILED.getCode(), "角色不存在");
        }

        return role;
    }

    @Override
    @Caching(put = {@CachePut(value = "roles", key = "#result.id", condition = "#result != null")})
    public Role add(Role role) {
        // 判断角色编码是否被占用
        long codeCnt = roleMapper.countByCode(role.getCode());
        if (codeCnt > 0L) {
            throw new APIException(ResultCode.FAILED.getCode(), "角色编码已被占用");
        }
        // 保存角色
        Role saveEntity = new Role();
        saveEntity.setCode(role.getCode());
        saveEntity.setName(role.getName());
        roleMapper.insert(saveEntity);
        // 保存权限
        saveRolePermission(role.getId(), role.getPermissionIds());
        return saveEntity;
    }

    @Override
    @Caching(put = {@CachePut(value = "roles", key = "#result.id", condition = "#result != null")},
            evict = {@CacheEvict(value = "user-permissions", allEntries = true, condition = "#result != null")})
    public Role edit(Role role) {
        // 查询修改角色
        Role saveEntity = this.info(role.getId());
        // 修改角色
        saveEntity.setName(role.getName());
        roleMapper.updateById(saveEntity);
        // 修改权限
        Set<String> permissionIds = role.getPermissionIds() == null ? new HashSet<>(1) : role.getPermissionIds();
        Set<String> havePermissionIds = saveEntity.getPermissionIds() == null ? new HashSet<>(1) : saveEntity.getPermissionIds();
        if (!CollectionUtils.isEqualCollection(permissionIds, havePermissionIds)) {
            rolePermissionService.deleteByRoleId(role.getId());
            saveRolePermission(role.getId(), permissionIds);
        }

        return saveEntity;
    }

    /**
     * 保存角色权限
     *
     * @param roleId        角色id
     * @param permissionIds 权限id集合
     */
    private void saveRolePermission(Long roleId, Set<String> permissionIds) {
        if (CollectionUtils.isNotEmpty(permissionIds)) {
            List<RolePermission> rolePermissions = new ArrayList<>(permissionIds.size());
            permissionIds.forEach(i -> {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(i);
                rolePermissions.add(rolePermission);
            });
            rolePermissionService.saveBatch(rolePermissions);
        }
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "roles", key = "#id", condition = "#id != null")})
    public Status status(Long id, Status status) {
        // 查询修改角色
        Role saveEntity = this.info(id);
        // 修改角色状态
        saveEntity.setStatus(status);
        roleMapper.updateById(saveEntity);

        return status;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "roles", key = "#id", condition = "#id != null")})
    public void delete(Long id) {
        roleMapper.deleteById(id);
    }
}
