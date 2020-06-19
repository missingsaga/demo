package com.loki.demo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户DTO
 *
 * @author Loki
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -278748818121294403L;

    /**
     * 主键
     */
    private Long id;
}
