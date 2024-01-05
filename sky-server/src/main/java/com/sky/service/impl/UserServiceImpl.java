package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import io.lettuce.core.ScriptOutputType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: HHNGDCZ
 * @since: 2024/01/05/17:28
 * @description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    /**
     * 微信登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxlogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO);
        //判空
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断是否是新用户
        User user = userMapper.getByOpenid(openid);
        //新用户注册
        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();

            userMapper.insert(user);
        }


        return user;
    }

    /**
     * 获取openid
     * @param userLoginDTO
     * @return
     */
    private String getOpenid(UserLoginDTO userLoginDTO) {
        //获得openId
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", userLoginDTO.getCode());
        map.put("grant_type", "authorization_code");
        System.out.println(map);
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
