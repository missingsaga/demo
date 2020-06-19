package com.loki.demo.exception;

import lombok.Getter;

/**
 * 自定义API异常
 *
 * @author Loki
 */
@Getter //只要getter方法，无需setter
public class APIException extends RuntimeException {

    private static final long serialVersionUID = 8852256061744746130L;

    private int code;

    private String msg;

    public APIException() {
        this(1001, "接口错误");
    }

    public APIException(String msg) {
        this(1001, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
