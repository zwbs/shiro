package com.example.shiro.service.impl;

import com.example.shiro.bean.User;
import com.example.shiro.mapper.UserMapper;
import com.example.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int addUser(User user) throws NoSuchAlgorithmException {

        String[] split = UUID.randomUUID().toString().split("-");
        String password = user.getPassword();
        String salt = split[0];
        user.setSalt(salt);

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(salt.getBytes());
        String s = Hex.encodeToString(md5.digest(password.getBytes()));
        user.setPassword(s);
        return userMapper.addUser(user);
    }

    @Override
    public User getByUsername(String username) {
        User user = userMapper.getByUsername(username);
        return user;
    }

}
