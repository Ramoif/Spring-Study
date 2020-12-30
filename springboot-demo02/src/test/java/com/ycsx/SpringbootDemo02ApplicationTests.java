package com.ycsx;

import com.ycsx.pojo.Dog;
import com.ycsx.pojo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootDemo02ApplicationTests {

    /*装配dog类*/
    @Autowired
    private Dog dog;
    @Autowired
    private Person person;

    @Test
    void contextLoads() {
        System.out.println(dog);
        System.out.println(person);
    }

}
