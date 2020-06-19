package com.loki.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loki.demo.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * 用户管理Mapper
 *
 * @author Loki
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据编码统计
     *
     * @param code 编码
     * @return
     */
    @Select("SELECT COUNT(1) FROM `user` WHERE code = #{code}")
    long countByCode(String code);
}
