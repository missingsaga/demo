package com.loki.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.loki.demo.annotation.ExceptionCode;
import com.loki.demo.validation.Create;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * 用户
 *
 * @author Loki
 *
 */
@Data
public class User extends BaseEntity {

    private static final long serialVersionUID = -8729078717143029004L;

    /**
     * 编码
     */
    @NotBlank(groups = {Create.class}, message = "编码不能为空")
    @Size(max = 30, message = "编码长度不能超过30个字符")
    @ExceptionCode(value = 100001, message = "编码验证错误")
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 30, message = "名称长度不能超过30个字符")
    @ExceptionCode(value = 100002, message = "名称验证错误")
    private String name;

    /**
     * 账号
     */
    @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    @ExceptionCode(value = 100003, message = "账号验证错误")
    private String account;

    /**
     * 密码
     */
    @Size(min = 6, max = 11, message = "密码长度必须是6-11个字符")
    @ExceptionCode(value = 100004, message = "密码验证错误")
    private String password;

    /**
     * 角色id集合
     */
    @TableField(exist = false)
    private Set<Long> roleIds;

}
