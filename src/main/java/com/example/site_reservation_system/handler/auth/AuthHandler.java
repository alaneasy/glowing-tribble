package com.example.site_reservation_system.handler.auth;

/*
 * Created By Alan on 2022/1/11
 * */

import com.example.site_reservation_system.model.visitor.Visitor;
import com.example.site_reservation_system.service.http.HttpWeChatApi;
import com.example.site_reservation_system.service.visitor.VisitorService;
import com.example.site_reservation_system.util.result.Result;
import com.example.site_reservation_system.util.result.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@Api
@RestController
@RequestMapping("/auth")
public class AuthHandler {

    @Autowired
    private VisitorService userService;
    @Autowired
    private HttpWeChatApi httpWeChatApi;

    /*
     * “id” : 手机号
     * “uuid” : 微信授权后从Redis取出openId
     * */

    @ApiOperation("登入")
    @PostMapping("/login")
    public Result login(String code) throws IOException, URISyntaxException {
        String id = httpWeChatApi.getUserPhoneNumber(code).getJSONObject("phone_info").getString("purePhoneNumber");
        if (id == null) {
            return ResultFactory.buildErrorResult("请登录微信");
        }
        if (SecurityUtils.getSubject().getPrincipal() != null) {
            SecurityUtils.getSubject().logout();
        }
        Visitor user = userService.queryById(id);
        if (user == null) {
            user = Visitor.builder().id(id).build();
            userService.insert(user);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getId(), user.getId());
        subject.login(usernamePasswordToken);
        return ResultFactory.buildSuccessResult().put("tokenId", subject.getSession().getId());
    }

    /*
     * 授权登出
     * */
    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return ResultFactory.buildSuccessResult();
    }
}