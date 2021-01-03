package com.ycsx.controller;

import com.ycsx.mapper.BookMapper;
import com.ycsx.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookMapper bookMapper;

    @GetMapping("/queryBookList")
    public List<Book> queryUserList(){
        List<Book> bookList = bookMapper.queryBookList();
        for (Book book : bookList) {
            System.out.println(book);
        }
        return bookList;
    }
}
