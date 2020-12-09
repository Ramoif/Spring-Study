package com.ycsx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestFulController {
/*    @RequestMapping("/add/{a}/{b}")
    public String test1(@PathVariable int a,@PathVariable int b, Model model) {
        int res = a + b;
        //这里定义的a，b必须在url后面加上"?a=?&b=?"，不然会报服务器错误。
        model.addAttribute("msg", "结果1是" + res);
        return "test";
    }*/
    //同路径不同请求。
    @GetMapping("/add/{a}/{b}")
    public String test2(@PathVariable int a,@PathVariable int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", "结果2是" + res);
        return "test";
    }
    @PostMapping("/add/{a}/{b}")
    public String test3(@PathVariable int a,@PathVariable int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", "结果3是" + res);
        return "test";
    }
}
