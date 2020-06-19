package com.loki.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loki.demo.entity.UserRole;
import com.loki.demo.mapper.UserRoleMapper;
import com.loki.demo.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 用户角色关系Service实现
 *
 * @author Loki
 */
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    private final UserRoleMapper userRoleMapper;

    @Override
    public boolean saveBatch(List<UserRole> userRoles) {
        return super.saveBatch(userRoles, 1000);
    }

    @Override
    public int deleteByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        return userRoleMapper.delete(queryWrapper);
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getRoleId, roleId);
        return userRoleMapper.delete(queryWrapper);
    }

    @Override
    public List<UserRole> selectByUserIds(Set<Long> userIds) {
        return userRoleMapper.selectByUserIds(userIds);
    }

    @Override
    public Set<Long> selectByUserId(Long userId) {
        return userRoleMapper.selectByUserId(userId);
    }

}
