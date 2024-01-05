package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: HHNGDCZ
 * @since: 2024/01/04/21:21
 * @description:
 */

@RestController
@RequestMapping("/user/user")
@Api(tags = "C端用户接口")
@Slf4j
public class UserController {



    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;
    /**
     * 查询店铺营业状态
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("C端用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){

        log.info("微信用户登录：{}",userLoginDTO.getCode());
        User user = userService.wxlogin(userLoginDTO);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims
        );
      UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
              .build();

        return Result.success(userLoginVO);
    }
}
