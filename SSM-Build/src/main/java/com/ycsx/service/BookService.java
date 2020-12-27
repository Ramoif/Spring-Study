package com.ycsx.service;

import com.ycsx.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookService {
    /*第九步，此处直接引用BookMapper中的方法。*/
    int addBook(Books books);

    int deleteBook(int id);

    int updateBook(Books books);

    Books queryBookById(int id);

    List<Books> queryBookByName(String bookName);

    List<Books> queryAllBook();
}
