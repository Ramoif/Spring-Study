package com.ycsx.controller;

import com.ycsx.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {
    @RequestMapping("/t1")
    public String test(){
        return "hello";
    }
    @RequestMapping("/a1")
    public void a1(String name, HttpServletResponse response) throws IOException {
        System.out.println("a1:param=>"+name);
        if("abc".equals(name)){
            response.getWriter().print("true");
        }else {
            response.getWriter().print("false");
        }
    }
    @RequestMapping("/a2")
    public List<User> a2(){
        List<User> userList = new ArrayList<>();
        /*添加数据*/
        userList.add(new User("用户Jack",18,"男"));
        userList.add(new User("用户Mick",27,"男"));
        userList.add(new User("用户John",36,"男"));
        return userList;
    }
}
