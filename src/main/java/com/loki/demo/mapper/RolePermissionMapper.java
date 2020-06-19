package com.loki.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loki.demo.entity.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 角色权限关系Dao
 * 
 * @author Loki
 *
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 根据角色id查询权限字符串集合
     * 
     * @param roleId
     *            角色id
     * @return
     */
    @Select("SELECT permission_id FROM `role_permission` WHERE role_id = #{roleId}")
    Set<String> selectByRoleId(Long roleId);

    /**
     * 根据角色id集合查询权限字符串集合
     * 
     * @param roleIds
     *            角色id集合
     * @return
     */
    @Select({ 
        "<script>",
            "SELECT permission_id FROM `role_permission` WHERE role_id in",
            "<foreach collection='roleIds' item='id' open='(' separator=',' close=')'>",
                "#{id}", 
            "</foreach>", 
        "</script>" })
    Set<String> selectByRoleIds(@Param("roleIds") Set<Long> roleIds);
}
