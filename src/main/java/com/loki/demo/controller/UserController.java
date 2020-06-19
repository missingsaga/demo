package com.loki.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.loki.demo.annotation.CurrentUser;
import com.loki.demo.annotation.NotResponseBody;
import com.loki.demo.dto.UserDTO;
import com.loki.demo.entity.User;
import com.loki.demo.enums.Status;
import com.loki.demo.service.UserService;
import com.loki.demo.validation.Create;
import com.loki.demo.validation.Update;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

/**
 * 用户管理控制器
 *
 * @author Loki
 */
@RestController
@RequestMapping("portal/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户分页查询
     *
     * @param word     模糊查询条件
     * @param pageable 分页对象
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @NotResponseBody
    @RequiresPermissions("user:view")
    public IPage<User> page(@CurrentUser UserDTO userDto, String word, Pageable pageable) {
        return userService.page(word, pageable);
    }

    /**
     * 用户添加
     *
     * @param user 用户对象
     * @return
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("user:add")
    public User add(@CurrentUser UserDTO userDto, @RequestBody @Validated(value = {Default.class, Create.class}) User user) {
        return userService.add(user);
    }

    /**
     * 用户修改
     *
     * @param user 用户对象
     * @return
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("user:edit")
    public User edit(@CurrentUser UserDTO userDto, @RequestBody @Validated(value = {Default.class, Update.class}) User user) {
        return userService.edit(user);
    }

    /**
     * 用户状态变更
     *
     * @param id     用户id
     * @param status 状态
     * @return
     */
    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("user:status")
    public Status status(@CurrentUser UserDTO userDto, Long id, Status status) {
        return userService.status(id, status);
    }

    /**
     * 用户删除
     *
     * @param id 用户id
     */
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("user:delete")
    public void delete(Long id) {
        userService.delete(id);
    }
}
