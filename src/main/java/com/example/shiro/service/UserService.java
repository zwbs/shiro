package com.example.shiro.service;

import com.example.shiro.bean.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    int addUser(User user) throws NoSuchAlgorithmException;

    User getByUsername(String username);
}
