package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: HHNGDCZ
 * @since: 2024/01/05/17:36
 * @description:
 */
@Mapper
public interface UserMapper {
    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid=#{openid}")
    User getByOpenid(String openid);


    /**
     * 新增用户
     */

    void insert(User user);
}
