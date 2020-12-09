package com.ycsx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ycsx.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FastJsonController {
    @RequestMapping("/fj1")
    public String fastJson1() {
        List<User> userList = new ArrayList<User>();
        User user1 = new User(1, "李1号", "男");
        User user2 = new User(2, "李2号", "男");
        User user3 = new User(3, "李3号", "男");
        User user4 = new User(4, "李4号", "男");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        String str = JSON.toJSONString(userList);
        return str;
    }

    //一个alibabaFastJson的综合例子
    @RequestMapping("/fj2")
    public String fastJson2() {
        List<User> userList = new ArrayList<User>();
        User user1 = new User(1, "李1号", "男");
        User user2 = new User(2, "李2号", "男");
        User user3 = new User(3, "李3号", "男");
        User user4 = new User(4, "李4号", "男");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        System.out.println("***** Java对象 转换 Json字符串 *****");
        String str1 = JSON.toJSONString(userList);
        System.out.println("userList转换为了Json字符串 = " + str1);
        String str2 = JSON.toJSONString(user1);
        System.out.println("user1转换为Json字符串 = " + str2);

        System.out.println("\n***** Json字符串 转换 Java对象 *****");
        User jp_user1 = JSON.parseObject(str2,User.class);
        System.out.println("把前端Json变成了Java对象打印 = " + jp_user1);

        System.out.println("\n***** Java对象 转换 Json“对象” *****");
        JSONObject jsonObject1 = (JSONObject) JSON.toJSON(user2);
        System.out.println("user2被转换成了JSON对象，键值对name(名字)：" + jsonObject1.getString("name"));
        System.out.println("user2被转换成了JSON对象，键值对sex(性别)：" + jsonObject1.getString("sex"));

        System.out.println("\n***** JSON对象 转换 Java对象 *****");
        User to_java_user = JSON.toJavaObject(jsonObject1,User.class);
        System.out.println("把Json对象转回了Java对象 = " + to_java_user);
        return "hello";
    }
}
