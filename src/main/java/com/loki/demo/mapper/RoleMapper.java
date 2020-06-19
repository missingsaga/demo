package com.loki.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loki.demo.entity.Role;
import org.apache.ibatis.annotations.Select;

/**
 * 角色管理Mapper
 *
 * @author Loki
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据编码统计
     *
     * @param code 编码
     * @return
     */
    @Select("SELECT COUNT(1) FROM `role` WHERE code = #{code}")
    long countByCode(String code);
}
