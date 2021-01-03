package com.ycsx.mapper;

import com.ycsx.pojo.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface BookMapper {
    List<Book> queryBookList();
    Book queryBookById(int bookID);
    int addBook(Book book);
    int updateBook(Book book);
    int deleteBook(Book book);
}
