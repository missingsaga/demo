package com.loki.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.loki.demo.enums.Deleted;
import com.loki.demo.enums.Status;
import com.loki.demo.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 基类
 *
 * @author Loki
 *
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 5144769240726477187L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @NotNull(groups = {Update.class}, message = "主键id不能为空")
    private Long id;

    /**
     * 状态
     */
    @TableField(fill = FieldFill.INSERT)
    private Status status;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 更新人id
     */
    private Long updateId;

    /**
     * 创建人名称
     */
    @TableField(exist = false)
    private String createName;

    /**
     * 更新人名称
     */
    @TableField(exist = false)
    private String updateName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    /**
     * 删除标识
     */
    @TableLogic
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Deleted deleted;
}
