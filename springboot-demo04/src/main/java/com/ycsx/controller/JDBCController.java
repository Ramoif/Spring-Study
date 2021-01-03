package com.ycsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JDBCController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /*查询数据库的所有信息(没有实体类如何查询)*/
    @GetMapping("/bookList")
    public List<Map<String, Object>> userList() {
        String sql = "select * from books";
        List<Map<String, Object>> list_maps = jdbcTemplate.queryForList(sql);
        return list_maps;
    }

    //增加一本书
    @GetMapping("/addBook")
    public String addBook() {
        String sql = "insert into books(bookID,bookName,bookCounts,detail) values(10,'Mybatis',20,'从入坑到独创');";
        jdbcTemplate.update(sql);
        return "add-AllRight";
    }

    //改
    @GetMapping("/update/{bookID}")
    public String updateBook(@PathVariable("bookID") int id) {
        String sql = "update books set bookName=?,bookCounts=?,detail=? where bookID=" + id;
        Object[] objects = new Object[3];
        objects[0] = "MybatisPlus";
        objects[1] = 21;
        objects[2] = "从mybatis到Plus";
        jdbcTemplate.update(sql,objects);
        return "update-AllRight";
    }
}
