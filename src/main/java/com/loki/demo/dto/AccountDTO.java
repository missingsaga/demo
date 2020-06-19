package com.loki.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 账号DTO
 *
 * @author Loki
 */
@Data
public class AccountDTO implements Serializable {

    private static final long serialVersionUID = -2543359952730645431L;

    /**
     * 账号
     */
    @NotBlank(message = "请输入登陆账号")
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "请输入登陆密码")
    private String password;
}
