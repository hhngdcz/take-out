package com.sky.utils;

import org.springframework.util.DigestUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: HHNGDCZ
 * @since: 2024/01/03/13:18
 * @description:
 */
public class MD5Util {
    public static void main(String[] args) {
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        System.out.println(password);
    }
}
