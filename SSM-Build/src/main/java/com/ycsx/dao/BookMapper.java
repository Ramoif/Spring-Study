package com.ycsx.dao;

import com.ycsx.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*dao层的Mapper做的都是接口操作，他们的实现全部通过xml-mybatis实现*/
public interface BookMapper {
    /*比如增删改查*/
    int addBook(Books books);

    /*这里利用别名来对应数据库中的关键词，在xml文件中填入#{bookId}*/
    int deleteBook(@Param("bookID") int id);

    int updateBook(Books books);

    Books queryBookById(int id);

    /*模糊查询*/
    List<Books> queryBookByName(@Param("bookName") String bookName);

    List<Books> queryAllBook();
}
