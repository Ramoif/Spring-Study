package com.ycsx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*四个问题：
* 1、@Controller的作用是什么？
* 2、@RequestMapping有别的写法吗？
* 3、model怎么封装？
* 4、return的值是怎么跳转的？*/

@Controller
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/h1")
    public String hello(Model model) {
        model.addAttribute("msg", "Hello,SpringMVC-annotation!");
        return "hello"; //会被视图解析器处理。
    }
}
