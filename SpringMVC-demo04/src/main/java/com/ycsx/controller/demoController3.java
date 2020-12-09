package com.ycsx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/c3")
public class demoController3 {
    @RequestMapping("/t3")
    public String test3(Model model){
        model.addAttribute("msg","demoController3");
        return "test";
    }
}
