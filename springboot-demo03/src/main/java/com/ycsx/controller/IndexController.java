package com.ycsx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/*在templates目录下的所有页面只能用Controller来跳转
* 还有模板引擎的支持*/
@Controller
public class IndexController {

    @RequestMapping("/test")
    public String test(Model model){
        /*测试转义*/
        model.addAttribute("msg","<h1>msg</h1>");
        /*遍历*/
        model.addAttribute("users", Arrays.asList("用户1","用户2"));
        return "test";
    }
}
