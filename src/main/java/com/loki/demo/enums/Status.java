package com.loki.demo.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 状态枚举类
 *
 * @author Loki
 *
 */
@Getter
public enum Status implements IEnum<Integer> {

    ENABLE(1, "启用"), DISABLE(2, "禁用");

    private Integer value;

    private String name;

    Status(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

}
