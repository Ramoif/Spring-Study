重定向与转发-SpringMVC方式实现
```text
ServletAPI中的req.getRequestDispatcher().forward() -> 例子
url不变->转发(一次请求)这是默认方法
return "/WEB-INF/jsp/test.jsp";
重定向（两次请求）
return "redirect:/WEB-INF/jsp/test.jsp";
```
Lombok的回顾使用
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private int age;
}
```
接受传参-数据处理(普通参数)
```java
@Controller
@RequestMapping("/user")
public class UserController {
    //标准请求：localhost:8080/user/t1?name=xxx;
    //第二种方式：localhost:8080/user/t1?username=XXX;
    //如果不设置@RequestParam("username")的话，那么前端?name必须和传参名name相同。
    @GetMapping("/t1")
    public String test1(@RequestParam("username") String name, Model model) {
        //1.接收参数
        System.out.println("接受到了前端参数" + name + "。");
        model.addAttribute("msg",name);
        //2.返回结果
        return "test";
    }
}
```
如果是一个对象？
```java
//前端传参：localhost:8080/user/t2?id=1&name=wangwu&age=18
//如果要传递对象...如果请求的url中?参数和User对象中的参数名一致，则可以匹配，如果不一致则为Null。
    @GetMapping("/t2")
    public String test2(User user, Model model) {
        System.out.println(user);
        return "test";
    }
//后端打印：User(id=1, name=王五, age=18)
```
web一定会出现的乱码问题的解决办法(1.过滤器解决)
```java
//提交的时候就乱码了，所以需要过滤器来解决问题。
public class EncodingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {  }
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        filterChain.doFilter(servletRequest,servletResponse);
    }
    public void destroy() {    }
}
//然后要去web.xml注册。
/**
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>com.ycsx.filter.EncodingFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
*/
```
当然要用Spring方法解决：（web.xml）
```xml
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```
Tomcat配置也可以来解决乱码。
