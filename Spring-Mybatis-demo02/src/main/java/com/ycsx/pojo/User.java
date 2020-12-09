package com.ycsx.pojo;

import lombok.Data;

/*一定要和数据库中的元素名对应。*/
@Data
public class User {
    private int u_id;

    public User(int u_id, String u_name, String u_pw, String u_control) {
        this.u_id = u_id;
        this.u_name = u_name;
        this.u_pw = u_pw;
        this.u_control = u_control;
    }

    private String u_name;
    private String u_pw;
    private String u_control;
}
