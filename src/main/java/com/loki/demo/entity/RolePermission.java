package com.loki.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色权限关系
 *
 * @author
 */
@Data
public class RolePermission implements Serializable {

    private static final long serialVersionUID = -1828541285984592829L;

    /**
     * 角色id
     */
    @TableId(type = IdType.INPUT)
    private Long roleId;

    /**
     * 权限id
     */
    @TableId(type = IdType.INPUT)
    private String permissionId;
}
