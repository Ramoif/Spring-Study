import com.ycsx.pojo.*;
import com.ycsx.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest01 {
    /* xml加载，获取Spring的上下文对象。对象在Spring中管理。默认是“单例模式”(加载配置文件的时候已经初始化)！*/
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Hello hello = (Hello) context.getBean("hello");
        System.out.println(hello);
    }

    /* 要修改打印的值，现在需要去xml文件修改对应位置的值(ref 或者 value) */
    @Test
    public void test02() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserServiceImpl userServiceImpl = (UserServiceImpl) context.getBean("UserServiceImpl");
        userServiceImpl.getUser();
    }

    /* 调用beans.xml文件的时候，里面的对象都会被默认先创建，比如下面没有使用hello对象但是仍然调用了hello的无参数构造方法（默认） */
    @Test
    public void test03() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        User user = (User) context.getBean("user");
        user.show();
    }

    /* 测试多属性的Student类注入 */
    @Test
    public void test04() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Student student = (Student) context.getBean("student");
        System.out.println(student.toString());
    }

    /* getBean的第二个参数，指定类型不用强转换了！ */
    @Test
    public void test05() {
        ApplicationContext context = new ClassPathXmlApplicationContext("userbeans.xml");
        People people = context.getBean("people", People.class);
        System.out.println(people);
    }

    /* 装填方式测试 */
    @Test
    public void test06() {
        ApplicationContext context = new ClassPathXmlApplicationContext("userbeans.xml");
        Person person = context.getBean("person", Person.class);
        System.out.println(person);
        person.getCat().shout();
        person.getDog().shout();
    }

}

