package com.loki.demo.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.Getter;

/**
 * 删除标识枚举类
 *
 * @author Loki
 */
@Getter
public enum Deleted implements IEnum<Boolean> {

    FALSE(false, "否"), TRUE(true, "是");

    private Boolean value;

    private String name;

    Deleted(Boolean value, String name) {
        this.value = value;
        this.name = name;
    }
}
