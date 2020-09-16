package com.example.shiro.matcher;

import com.example.shiro.bean.User;
import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Component
public class MyMatcher extends Md5CredentialsMatcher {



    @SneakyThrows
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        SimpleAuthenticationInfo info1 = (SimpleAuthenticationInfo) info;
        String password = String.valueOf(token1.getPassword());
        String pwd = (String) info1.getCredentials();
        ByteSource salt = info1.getCredentialsSalt();

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(salt.getBytes());
        String s = Hex.encodeToString(md5.digest(password.getBytes()));
        if (s.equals(pwd)){
            return true;
        }

        return false;

    }



}
