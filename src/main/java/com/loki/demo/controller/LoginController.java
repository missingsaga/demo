package com.loki.demo.controller;

import com.loki.demo.dto.AccountDTO;
import com.loki.demo.dto.UserDTO;
import com.loki.demo.entity.User;
import com.loki.demo.enums.ResultCode;
import com.loki.demo.exception.APIException;
import com.loki.demo.service.UserService;
import com.loki.demo.utils.CookieUtils;
import com.loki.demo.utils.JacksonUtils;
import com.loki.demo.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;
import java.util.Objects;

/**
 * 登陆控制器
 *
 * @author Loki
 */
@RestController
@RequestMapping("pages")
@RequiredArgsConstructor
public class LoginController {

    private final JwtUtils jwtUtils;

    private final UserService userService;

    /**
     * 登陆
     *
     * @param request  Http请求
     * @param response Http响应
     * @param account  登陆账号密码
     * @return token
     */
    @PostMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response, @RequestBody @Validated(value = {Default.class}) AccountDTO account) {
        User user = userService.selectByAccount(account);
        if (Objects.isNull(user)) {
            throw new APIException(ResultCode.FAILED.getCode(), "账号或密码错误！");
        }
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        String token = jwtUtils.sign(JacksonUtils.write2JsonString(userDto));
        CookieUtils.setCookie(request, response, "token", token);
        return token;
    }

    /**
     * 系统注销
     *
     * @param request HttpRequest请求
     * @return
     */
    @DeleteMapping(value = "logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, "token");
    }
}
