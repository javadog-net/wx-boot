package net.javadog.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.chanjar.weixin.common.error.WxErrorException;
import net.javadog.wx.common.response.ResponseData;
import net.javadog.wx.entity.User;

/**
 * 用户接口
 *
 * @author: hdx
 * @Date: 2023-01-10 11:53
 * @version: 1.0
 **/
public interface UserService extends IService<User> {

    ResponseData auth(String code) throws WxErrorException;

}
