package com.loki.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.loki.demo.entity.Role;
import com.loki.demo.enums.Status;
import org.springframework.data.domain.Pageable;

/**
 * 角色管理Service
 *
 * @author Loki
 */
public interface RoleService {
    
    /**
     * 角色分页查询
     *
     * @param word     模糊查询条件
     * @param pageable 分页对象
     * @return
     */
    IPage<Role> page(String word, Pageable pageable);

    /**
     * 角色详情查询
     *
     * @param id 角色id
     * @return
     */
    Role info(Long id);

    /**
     * 角色添加
     *
     * @param role 角色添加
     * @return
     */
    Role add(Role role);

    /**
     * 角色修改
     *
     * @param role 角色对象
     * @return
     */
    Role edit(Role role);

    /**
     * 角色状态变更
     *
     * @param id     角色id
     * @param status 状态
     * @return
     */
    Status status(Long id, Status status);

    /**
     * 角色删除
     *
     * @param id 角色id
     */
    void delete(Long id);
}
