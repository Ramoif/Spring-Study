使用注解springMVC
```text
第一步，设置pom.xml过滤，环境设置lib导入jar包。
第二步，添加web框架，配置web.xml文件。
第三步，配置springmvc-servlet.xml文件。
原来的配置需要：
    处理器映射器 BeanNameUrlHandlerMapping
    处理器适配器 SimpleControllerHandlerAdapter
    视图解析器 InternalResourceViewResolver
        现在取代了前两项。
        <!--1.自动扫描包-->
        <content:component-scan base-package="com.ycsx.controller"/>
        <!--2.不处理静态资源(.css .js .html .mp3 .mp4)-->
        <mvc:default-servlet-handler/>
        <!--3.使用annotation-driven取代Mapping和Adapter。他会自动注入，开启。-->
        <mvc:annotation-driven/>
        4.仍然要配置视图解析器InternalResourceViewResolver。
第四步，编写servlet。
        1.在类名前加上@Controller，他会自动装配。不需要写bean了。
        2.写方法。下面举例一个。
（下面的例子解析：类上的@RequestMapping代表真实访问的地址是：项目名/HelloController/hello，在方法前写会在/hello后拼接。）
```
一个Controller的例子
```java
//真实地址：(http://localhost:8080/hello/h1)
@Controller
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/h1")
    public String hello(Model model) {
        model.addAttribute("msg", "Hello,SpringMVC-annotation!");
        return "hello"; //会被视图解析器处理。拼接头部和尾部。
    }
}
```
上面例子的几个问题
```text
四个问题：
* 1、@Controller的作用是什么？
Controller：控制器
控制器负责解析用户的请求，并且将其转换成一个模型。
返回值是一个ModelAndView是因为Controller接口的实现。(?)
因为使用接口装填比较麻烦，所以一般使用注解的方式实现。
和@Controller同样功能的其他注解有：
@Component组件 @Service @Controller @Repository
使用了注解则需要配置自动扫描。<content:>
使用了注解则代表了这个类会被Spring托管。
被这个注解的类中的所有方法，如果返回值是String，并且有具体的页面可以跳转，那么就会被视图解析器解析。
例如return "hello" 他会自动去寻找 prefix + hello + suffix拼接成的地址。

* 2、@RequestMapping有别的写法吗？
2种写法：“注解在方法上面”和“同时注解类与方法”。
但是同时注解的方法写到方法内部("/x1/t1")会比较方便调试。
有父子嵌套关系。
* 3、model怎么封装？
封装到传递的参数中。(Model model)
然后方法中直接调用就可以了。

* 4、return的值是怎么跳转的？
在第一个问题的最后一个部分已经说明。
如果一个controller中写了多个@RequestMapping并且返回值都是同一个。
他会指向同一个页面（视图）。
如果通过在jsp中设置${}来设置addObject，你就可以发现不同的请求都是请求的同一个视图。
访问的方式不一样，但是可以设置自己的值。这说明视图是一个可复用的。

```
RestFul风格
```text
RestFul是一个资源定位以及资源操作的风格。
他不是标准，也不是协议，只是一种风格。基于这种风格开发更有层次，简洁。
传统式操作资源，通过url后面加?，method=?，&参数......url看起来非常的复杂。

功能（几个概念）
资源：互联网所有的事物都可以抽象为资源。
资源的操作：使用POST，DELETE，PUT，GET，不同的方法对资源进行操作。
分别对应添加、删除、修改、查询。

使用RestFul操作资源：可以通过不同的请求方式来实现请求地址相同，但是功能不同的效果！
例如MVC-demo04中的RestFulController案例：
原来的url：http://localhost:8080/add?a=1&b=2
使用RestFul：http://localhost:8080/add/a/b
上面的这种方式，需要使用@PathVariable注解(路径变量)，定义在方法上。
@RequestMapping("/add/{a}/{b}")
    public String test1(@PathVariable int a,@PathVariable int b, Model model) {...}
```
@RequestMapping注解能够处理HTTP请求的方法
```text
方法级别的注解有如下几个：组合注解
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@PatchMapping

@GetMapping("/add/{a}/{b}")
    等价于
@RequestMapping(value(或者path) = "/add/{a}/{b}", method = RequestMethod.GET)
```
同路径不同请求的例子
```java
//都是http://localhost:8080/add/1/2，请求地址都是相同的。但是结果不一样。URL的复用。
    @GetMapping("/add/{a}/{b}")
    public String test2(@PathVariable int a,@PathVariable int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", "结果2是" + res);
        return "test";
    }
    @PostMapping("/add/{a}/{b}")
    public String test3(@PathVariable int a,@PathVariable int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", "结果3是" + res);
        return "test";
    }
```