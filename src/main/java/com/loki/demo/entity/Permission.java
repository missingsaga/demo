package com.loki.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.loki.demo.annotation.ExceptionCode;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 权限
 *
 * @author Loki
 */
@Data
public class Permission implements Serializable {

    private static final long serialVersionUID = 326949342713413841L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    @Size(max = 30, message = "长度不能超过100个字符")
    private String id;

    /**
     * 上级主键
     */
    @Size(max = 30, message = "长度不能超过100个字符")
    private String parentId;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 30, message = "名称长度不能超过30个字符")
    @ExceptionCode(value = 100002, message = "名称验证错误")
    private String name;

    /**
     * 资源路径
     */
    @Size(max = 30, message = "长度不能超过100个字符")
    private String url;
}
