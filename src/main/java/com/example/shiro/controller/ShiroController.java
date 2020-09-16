package com.example.shiro.controller;

import com.example.shiro.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShiroController {

    @GetMapping("/add")
    @RequiresPermissions("add")
    public String add(){
        return "add";
    }

    @GetMapping("/update")
    @RequiresPermissions("update")
    public String update(){
        return "update";
    }

    @PostMapping("/login")
    public String login(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());

        try {
            subject.login(token);
            return "index";
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return "login";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

}
