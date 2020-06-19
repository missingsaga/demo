package com.loki.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loki.demo.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * 用户角色关系Dao
 * 
 * @author Loki
 *
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户id查询角色id集合
     * 
     * @param userId
     *            用户id
     * @return
     */
    @Select("SELECT role_id FROM `user_role` WHERE user_id = #{userId}")
    Set<Long> selectByUserId(Long userId);

    /**
     * 根据用户id集合查询用户角色关系
     * 
     * @param userIds
     *            用户id集合
     * @return
     */
    @Select({ 
        "<script>",
            "SELECT ur.* FROM `user_role` ur WHERE ur.user_id in",
            "<foreach collection='userIds' item='id' open='(' separator=',' close=')'>",
                "#{id}", 
            "</foreach>", 
        "</script>" })
    List<UserRole> selectByUserIds(@Param("userIds") Set<Long> userIds);
}
