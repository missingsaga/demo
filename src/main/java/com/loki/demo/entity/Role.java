package com.loki.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.loki.demo.annotation.ExceptionCode;
import com.loki.demo.validation.Create;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * 角色
 *
 * @author
 */
@Data
public class Role extends BaseEntity {

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
     * 权限id集合
     */
    @TableField(exist = false)
    private Set<String> permissionIds;

}
