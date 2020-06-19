package com.loki.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色关系
 *
 * @author
 */
@Data
public class UserRole implements Serializable {

    private static final long serialVersionUID = -5669848308112501188L;

    /**
     * 用户id
     */
    @TableId(type = IdType.INPUT)
    private Long userId;

    /**
     * 角色id
     */
    @TableId(type = IdType.INPUT)
    private Long roleId;
}
