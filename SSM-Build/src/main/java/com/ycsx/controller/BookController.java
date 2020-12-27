package com.ycsx.controller;

import com.ycsx.pojo.Books;
import com.ycsx.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    /*Controller调用Service层。需要注入service层*/
    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;

    /*第一个功能：查询所有书籍，并且展示*/
    @RequestMapping("/allBook")
    public String list(Model model) {
        List<Books> list = bookService.queryAllBook();
        model.addAttribute("list", list);
        /*返回到allBook页面，通过视图解析器自动拼接*/
        return "allBook";
    }

    /*第二个功能：增加书籍，这里是跳转到添加的页面*/
    @RequestMapping("/toAddBook")
    public String toAddPaper() {
        return "addBook";
    }

    /*第二个功能增加书籍的实现函数*/
    @RequestMapping("/addBook")
    public String addBook(Books books) {
        System.out.println("addBook=>" + books);
        bookService.addBook(books);
        /*重定向到全部书籍页面*/
        return "redirect:/book/allBook";
    }

    /*第三个业务-修改页面跳转，这里的int id对应的前端?id=传递的id参数*/
    @RequestMapping("/toUpdate")
    public String toUpdate(int id, Model model) {
        Books books = bookService.queryBookById(id);
        /*通过model设置数值*/
        model.addAttribute("QueryBooks", books);
        return "updateBook";
    }

    /*第三个业务-修改页面的提交实现*/
    @RequestMapping("/updateBook")
    public String updateBook(Books books) {
        System.out.println("updateBook=>" + books);
        bookService.updateBook(books);
        /*重定向可以实现再查询一次的功能*/
        return "redirect:/book/allBook";
    }

    /*第三.5个业务，删除（点击就删除了）*/
    /*这里复习RestFul风格。*/
    @RequestMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable("bookId") int id) {
        bookService.deleteBook(id);
        return "redirect:/book/allBook";
    }

    /*第四个业务，查询*/
    @RequestMapping("/queryBook")
    public String queryBookByName(String queryBookName, Model model) {
        List<Books> list = bookService.queryBookByName(queryBookName);
        model.addAttribute("list", list);
        return "allBook";
    }
}
