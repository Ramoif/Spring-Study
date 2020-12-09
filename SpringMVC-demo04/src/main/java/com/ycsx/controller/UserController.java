package com.ycsx.controller;

import com.ycsx.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    //标准请求：localhost:8080/user/t1?name=xxx;
    //第二种方式：localhost:8080/user/t1?username=XXX;
    //如果不设置@RequestParam("username")的话，那么前端?name必须和传参名name相同。
    @GetMapping("/t1")
    public String test1(@RequestParam("username") String name, Model model) {
        //1.接收参数
        System.out.println("接受到了前端参数" + name + "。");
        model.addAttribute("msg", name);
        //2.返回结果
        return "test";
    }

    //如果要传递对象...如果请求的url中?参数和User对象中的参数名一致，则可以匹配，如果不一致则为Null。
    @GetMapping("/t2")
    public String test2(User user, Model model) {
        System.out.println(user);
        return "test";
    }
}
