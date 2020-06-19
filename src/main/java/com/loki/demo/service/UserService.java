package com.loki.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.loki.demo.dto.AccountDTO;
import com.loki.demo.entity.User;
import com.loki.demo.enums.Status;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * 用户管理Service
 *
 * @author Loki
 */
public interface UserService {

    /**
     * 用户分页查询
     *
     * @param word     模糊查询条件
     * @param pageable 分页对象
     * @return
     */
    IPage<User> page(String word, Pageable pageable);

    /**
     * 用户详情查询
     *
     * @param id 用户id
     * @return
     */
    User info(Long id);

    /**
     * 用户添加
     *
     * @param user 用户添加
     * @return
     */
    User add(User user);

    /**
     * 用户修改
     *
     * @param user 用户对象
     * @return
     */
    User edit(User user);

    /**
     * 用户状态变更
     *
     * @param id     用户id
     * @param status 状态
     * @return
     */
    Status status(Long id, Status status);

    /**
     * 用户删除
     *
     * @param id 用户id
     */
    void delete(Long id);

    /**
     * 根据账号信息查询用户
     *
     * @param account 账号信息
     * @return
     */
    User selectByAccount(AccountDTO account);

    /**
     * 查询用户权限集合
     *
     * @param id 用户id
     * @return
     */
    Set<String> selectPermissions(Long id);
}
