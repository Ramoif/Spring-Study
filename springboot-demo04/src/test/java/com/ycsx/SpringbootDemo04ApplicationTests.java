package com.ycsx;

import com.ycsx.service.UserService;
import com.ycsx.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SpringbootDemo04ApplicationTests {
    @Autowired
    DataSource dataSource;

    @Autowired
    UserServiceImpl userService;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    @Test
    void test2(){
        System.out.println(userService.queryUserByName("admin"));
    }

}
