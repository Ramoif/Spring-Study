package com.ycsx.pojo;

import lombok.Data;

/*一定要和数据库中的元素名对应。*/
@Data
public class User {
    private int u_id;
    private String u_name;
    private String u_pw;
    private String u_control;
}
