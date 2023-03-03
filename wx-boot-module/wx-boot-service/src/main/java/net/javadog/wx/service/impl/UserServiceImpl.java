package net.javadog.wx.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.javadog.wx.common.response.ResponseData;
import net.javadog.wx.entity.User;
import net.javadog.wx.mapper.UserMapper;
import net.javadog.wx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户接口实现类
 *
 * @author: hdx
 * @Date: 2023-01-10 11:55
 * @version: 1.0
 **/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private WxMpService wxMpService;

    @Override
    public ResponseData auth(String code)  {
        WxOAuth2AccessToken accessToken;
        WxMpUser wxMpUser;
        try {
            accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
            log.info("AccessToken={}" + accessToken);
            wxMpUser = wxMpService.getUserService().userInfo(accessToken.getOpenId());
            log.info("wxMpUser={}", JSONUtil.toJsonStr(wxMpUser));
        } catch (WxErrorException e) {
            return ResponseData.error(e.getMessage());
        }
        return ResponseData.success(wxMpUser);
    }

}
