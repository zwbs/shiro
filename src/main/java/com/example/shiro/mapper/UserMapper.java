package com.example.shiro.mapper;

import com.example.shiro.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {


    int addUser(@Param("user") User user);

    User getByUsername(String username);
}
