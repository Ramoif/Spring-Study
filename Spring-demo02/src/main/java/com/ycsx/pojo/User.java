package com.ycsx.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/* Component组件。等价于<bean id="" class=""/>
*  Scope() 设置单例模式singleton和原型模式prototype */
@Component
@Data
@Scope("singleton")
public class User {
    /* 可以用在set方法上。 */
    @Value("Value赋值了张三")
    public String name;
}
