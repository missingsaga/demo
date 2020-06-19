package com.loki.demo.service;

import com.loki.demo.entity.UserRole;

import java.util.List;
import java.util.Set;

/**
 * 用户角色关系Service
 *
 * @author Loki
 */
public interface UserRoleService {

    /**
     * 批量保存用户角色关系
     *
     * @param userRoles 用户角色关系集合
     * @return
     */
    boolean saveBatch(List<UserRole> userRoles);

    /**
     * 根据用户id删除关系
     *
     * @param userId 用户id
     * @return
     */
    int deleteByUserId(Long userId);

    /**
     * 根据角色id删除关系
     *
     * @param roleId 角色id
     * @return
     */
    int deleteByRoleId(Long roleId);

    /**
     * 根据用户id集合查询关系
     *
     * @param userIds 用户id集合
     * @return
     */
    List<UserRole> selectByUserIds(Set<Long> userIds);

    /**
     * 根据用户id查询角色id集合
     *
     * @param userId 用户id
     * @return
     */
    Set<Long> selectByUserId(Long userId);
}
