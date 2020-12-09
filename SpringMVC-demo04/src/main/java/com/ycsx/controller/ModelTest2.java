package com.ycsx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ModelTest2 {
    @RequestMapping("/m1/t2")
    public String test(Model model){
        model.addAttribute("msg","ModelTest2设置。");
        //默认是直接转发。前面加redirect:就是重定向。
        return "test";
    }
}
