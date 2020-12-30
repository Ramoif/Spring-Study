package com.ycsx.springbootdemo01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        /*http://localhost:8080/hello/hello*/
        return "hello";
    }
}
