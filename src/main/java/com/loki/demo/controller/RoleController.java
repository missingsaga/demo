package com.loki.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.loki.demo.annotation.CurrentUser;
import com.loki.demo.dto.UserDTO;
import com.loki.demo.entity.Role;
import com.loki.demo.enums.Status;
import com.loki.demo.service.RoleService;
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
 * 角色管理控制器
 *
 * @author Loki
 */
@RestController
@RequestMapping("portal")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 角色分页查询
     *
     * @param word     模糊查询条件
     * @param pageable 分页对象
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("role:view")
    public IPage<Role> page(@CurrentUser UserDTO userDto, String word, Pageable pageable) {
        return roleService.page(word, pageable);
    }

    /**
     * 角色添加
     *
     * @param role 角色对象
     * @return
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("role:add")
    public Role add(@CurrentUser UserDTO userDto, @RequestBody @Validated(value = {Default.class, Create.class}) Role role) {
        return roleService.add(role);
    }

    /**
     * 角色修改
     *
     * @param role 角色对象
     * @return
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("role:edit")
    public Role edit(@CurrentUser UserDTO userDto, @RequestBody @Validated(value = {Default.class, Update.class}) Role role) {
        return roleService.edit(role);
    }

    /**
     * 角色状态变更
     *
     * @param id     角色id
     * @param status 状态
     * @return
     */
    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("role:status")
    public Status status(@CurrentUser UserDTO userDto, Long id, Status status) {
        return roleService.status(id, status);
    }

    /**
     * 角色删除
     *
     * @param id 角色id
     */
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions("role:delete")
    public void delete(Long id) {
        roleService.delete(id);
    }
}
