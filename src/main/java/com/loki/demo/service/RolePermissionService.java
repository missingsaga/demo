package com.loki.demo.service;

import com.loki.demo.entity.RolePermission;

import java.util.List;
import java.util.Set;

/**
 * 角色权限关系Service
 *
 * @author Loki
 */
public interface RolePermissionService {

    /**
     * 根据角色id查询权限id集合
     *
     * @param roleId 角色id
     * @return
     */
    Set<String> selectByRoleId(Long roleId);

    /**
     * 根据角色id集合查询权限字符串
     *
     * @param roleIds 角色权限集合
     * @return
     */
    Set<String> selectByRoleIds(Set<Long> roleIds);

    /**
     * 批量保存角色权限关系
     *
     * @param rolePermissions 角色权限关系集合
     * @return
     */
    boolean saveBatch(List<RolePermission> rolePermissions);

    /**
     * 根据角色id删除关系
     *
     * @param roleId 角色id
     * @return
     */
    int deleteByRoleId(Long roleId);

}
