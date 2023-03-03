package net.javadog.wx.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import net.javadog.wx.common.response.ResponseData;
import net.javadog.wx.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 鉴权控制器
 *
 * @author: hdx
 * @Date: 2022-12-07 15:48
 * @version: 1.0.0
 **/
@Api(tags = "网页授权控制器")
@RestController
@RequestMapping("/login")
public class AuthController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "网页授权获取用户基本信息", notes = "网页授权获取用户基本信息")
    @GetMapping
    public ResponseData login(@RequestParam String code) throws WxErrorException {
        return userService.auth(code);
    }
}
