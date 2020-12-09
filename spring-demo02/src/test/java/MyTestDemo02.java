import com.ycsx.config.demoConfig;
import com.ycsx.pojo.User;
import com.ycsx.pojo.User2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTestDemo02 {
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = context.getBean("user", User.class);
        System.out.println(user);
    }


    /* 纯Java配置（SpringBoot中大量存在?） */
    @Test
    public void javaConfigSpringTest01(){
        ApplicationContext context = new AnnotationConfigApplicationContext(demoConfig.class);
        User2 getUser = context.getBean("getUser", User2.class);
        System.out.println(getUser.getName());
    }
}
