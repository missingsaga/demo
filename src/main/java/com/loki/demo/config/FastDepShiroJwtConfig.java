package com.loki.demo.config;

import com.loki.demo.dto.UserDTO;
import com.loki.demo.service.UserService;
import com.loki.demo.utils.JacksonUtils;
import com.louislivi.fastdep.shirojwt.shiro.FastDepShiroJwtAuthorization;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.Set;

/**
 * FastDepShiroJwt配置类
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class FastDepShiroJwtConfig extends FastDepShiroJwtAuthorization {

    private final UserService userService;

    @Override
    public SimpleAuthorizationInfo getAuthorizationInfo(String userId) {
        UserDTO user = JacksonUtils.readJson2Entity(userId, UserDTO.class);
        // 查询该用户下的所有权限
        Set<String> collect = userService.selectPermissions(user.getId());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 添加用户权限到SimpleAuthorizationInfo中
        simpleAuthorizationInfo.addStringPermissions(collect);
        return simpleAuthorizationInfo;
    }

}
