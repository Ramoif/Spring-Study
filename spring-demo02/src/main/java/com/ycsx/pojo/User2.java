package com.ycsx.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User2 {
    private String name;

    @Value("用户2")
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User2{" +
                "name='" + name + '\'' +
                '}';
    }
}
