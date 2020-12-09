package com.ycsx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ycsx.pojo.User;
import com.ycsx.utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Controller RestController的作用就是返回字符串，取代了下面的ResponseBody
@RestController
public class UserController {
    @RequestMapping(value = "/j1", produces = "application/json;charset=utf-8")
    //@ResponseBody //不会走视图解析器，会直接返回一个字符串
    public String json1() throws JsonProcessingException {
        //jackson中的对象：ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1, "张三", "男");
        String str = mapper.writeValueAsString(user);
        return str;
    }

    @RequestMapping(value = "/j2")
    public String json2() throws JsonProcessingException {
        //jackson中的对象：ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(2, "李四", "男");
        String str = mapper.writeValueAsString(user);
        return str;
    }

    @RequestMapping(value = "/j3")
    public String json3() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<User> userList = new ArrayList<User>();
        User user1 = new User(1, "李1号", "男");
        User user2 = new User(2, "李2号", "男");
        User user3 = new User(3, "李3号", "男");
        User user4 = new User(4, "李4号", "男");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        String str = mapper.writeValueAsString(userList);
        return str;
    }

    //返回时间1
    @RequestMapping(value = "/j4")
    public String json4() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Date date = new Date();//这是返回时间戳Timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = mapper.writeValueAsString(sdf.format(date));
        return str;
    }

    //返回时间2
    @RequestMapping(value = "/j5")
    public String json5() throws JsonProcessingException {
        //1、创建Jackson对象
        ObjectMapper mapper = new ObjectMapper();
        //2、不适用时间戳的方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //3、自定义日期的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(sdf);
        //4、创建时间对象
        Date date = new Date();
        //5、返回
        return mapper.writeValueAsString(date);
    }

    //使用工具类返回时间3
    @RequestMapping(value = "/j6")
    public String json6() {
        Date date = new Date();
        return JsonUtils.getJson(date, "yyyy-MM-dd HH:mm:ss");
    }

    //工具类返回不带参数4
    @RequestMapping(value = "/j7")
    public String json7() {
        Date date = new Date();
        return JsonUtils.getJson(date);
    }

}
