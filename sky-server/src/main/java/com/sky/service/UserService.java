package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: HHNGDCZ
 * @since: 2024/01/05/17:20
 * @description:
 */

public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxlogin(UserLoginDTO userLoginDTO);


}
