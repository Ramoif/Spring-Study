package com.ycsx.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

public class LoginController {
    @RequestMapping("/user/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session){
        /*这里使用了简单粗暴的例子，只要用户名非空并且密码对上就能登录成功。*/
        if(!StringUtils.isEmpty(username)&&"123456".equals(password)){
            /*登录成功，设置session，只要存在就说明登录成功。*/
            session.setAttribute("loginUser",username);
            return "DashBoard";
        }else {
            /*登陆失败了*/
            model.addAttribute("msg","用户名或者密码错误");
            return "index";
        }
    }
}
