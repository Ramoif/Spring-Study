package com.ycsx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class demoServlet2 {
    @RequestMapping("/t2")
    public String test1(Model model){
        model.addAttribute("msg","demoServlet2设置了消息。");
        return "test";
    }
}
