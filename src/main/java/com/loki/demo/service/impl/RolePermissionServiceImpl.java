package com.loki.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loki.demo.entity.RolePermission;
import com.loki.demo.mapper.RolePermissionMapper;
import com.loki.demo.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色权限关系Service实现
 *
 * @author Loki
 */
@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    private final RolePermissionMapper rolePermissionMapper;

    @Override
    public Set<String> selectByRoleId(Long roleId) {
        return rolePermissionMapper.selectByRoleId(roleId);
    }

    @Override
    public Set<String> selectByRoleIds(Set<Long> roleIds) {
        Set<String> permissons = new HashSet<>(16);
        if (CollectionUtils.isNotEmpty(roleIds)) {
            permissons = rolePermissionMapper.selectByRoleIds(roleIds);
        }

        return permissons;
    }

    @Override
    public boolean saveBatch(List<RolePermission> rolePermissions) {
        return super.saveBatch(rolePermissions, 1000);
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleId, roleId);
        return rolePermissionMapper.delete(queryWrapper);
    }

}
