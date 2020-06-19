package com.loki.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loki.demo.dto.AccountDTO;
import com.loki.demo.entity.User;
import com.loki.demo.entity.UserRole;
import com.loki.demo.enums.Deleted;
import com.loki.demo.enums.ResultCode;
import com.loki.demo.enums.Status;
import com.loki.demo.exception.APIException;
import com.loki.demo.mapper.UserMapper;
import com.loki.demo.service.RolePermissionService;
import com.loki.demo.service.UserRoleService;
import com.loki.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用户管理Service实现
 *
 * @author Loki
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    private final UserRoleService userRoleService;

    private final RolePermissionService rolePermissionService;

    @Override
    public IPage<User> page(String word, Pageable pageable) {
        Page<User> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        return userMapper.selectPage(page, queryWrapper);
    }

    @Override
    @Cacheable(value = "users", key = "#id", unless = "#result eq null")
    public User info(Long id) {
        User user = userMapper.selectById(id);
        if (Objects.isNull(user)) {
            throw new APIException(ResultCode.FAILED.getCode(), "用户不存在");
        }
        Set<Long> roleIds = userRoleService.selectByUserId(id);
        user.setRoleIds(roleIds);

        return user;
    }

    @Override
    @Transactional
    @Caching(put = {@CachePut(value = "users", key = "#result.id", condition = "#result != null")})
    public User add(User user) {
        // 判断用户编码是否被占用
        long codeCnt = userMapper.countByCode(user.getCode());
        if (codeCnt > 0L) {
            throw new APIException(ResultCode.FAILED.getCode(), "用户编码已被占用");
        }
        // 保存用户
        User saveEntity = new User();
        saveEntity.setCode(user.getCode());
        saveEntity.setName(user.getName());
        saveEntity.setAccount(user.getAccount());
        saveEntity.setPassword(user.getPassword());
        saveEntity.setStatus(Status.ENABLE);
        saveEntity.setVersion(1);
        saveEntity.setDeleted(Deleted.FALSE);
        userMapper.insert(saveEntity);
        // 保存角色
        saveUserRole(user.getId(), user.getRoleIds());
        return saveEntity;
    }

    @Override
    @Transactional
    @Caching(put = {@CachePut(value = "users", key = "#result.id", condition = "#result != null")},
            evict = {@CacheEvict(value = "user-permissions", key = "#result.id", condition = "#result != null")})
    public User edit(User user) {
        // 查询修改用户
        User saveEntity = this.info(user.getId());
        // 修改用户
        saveEntity.setName(user.getName());
        saveEntity.setAccount(user.getAccount());
        saveEntity.setPassword(user.getPassword());
        userMapper.updateById(saveEntity);
        // 修改角色
        Set<Long> roleIds = user.getRoleIds() == null ? new HashSet<>(1) : user.getRoleIds();
        Set<Long> haveRoleIds = saveEntity.getRoleIds() == null ? new HashSet<>(1) : saveEntity.getRoleIds();
        if (!CollectionUtils.isEqualCollection(roleIds, haveRoleIds)) {
            userRoleService.deleteByUserId(user.getId());
            saveUserRole(user.getId(), roleIds);
        }

        return saveEntity;
    }

    /**
     * 保存用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     */
    private void saveUserRole(Long userId, Set<Long> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<UserRole> userRoles = new ArrayList<>(roleIds.size());
            roleIds.forEach(i -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(i);
                userRoles.add(userRole);
            });
            userRoleService.saveBatch(userRoles);
        }
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "users", key = "#id", condition = "#id != null")})
    public Status status(Long id, Status status) {
        // 查询修改用户
        User saveEntity = this.info(id);
        // 修改用户状态
        saveEntity.setStatus(status);
        userMapper.updateById(saveEntity);

        return status;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "users", key = "#id", condition = "#id != null")})
    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public User selectByAccount(AccountDTO account) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account.getAccount()).eq(User::getPassword, account.getPassword());
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    @Cacheable(value = "user-permissions", key = "#id", unless = "#result eq null || #result.size() < 1")
    public Set<String> selectPermissions(Long id) {
        Set<Long> roleIds = userRoleService.selectByUserId(id);
        return rolePermissionService.selectByRoleIds(roleIds);
    }
}
