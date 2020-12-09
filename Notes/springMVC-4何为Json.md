SpringMVC-demo05.md
=>Json
```text
Json是什么？
JSON(JavaScript Object Notation JS对象标记)
Json是前后端交互的一种数据交换格式。它是一种纯文本格式。
```
我们用Html来举一个Json转换的例子：
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript">
        //编写一个JavaScript对象 ES6
        var user = {
            name:"王五",
            age:18,
            sex:"男"
        };
        console.log(user);
        //将Js对象转换为Json
        var json = JSON.stringify(user);
        console.log(json);

        //把Json转换为Js对象
        var obj = JSON.parse(json);
        console.log(obj);
    </script>
</head>
<body>
</body>
</html>
```
目前常用的两种Json解析工具
```text
Jackson，阿里巴巴的fastJson，需要导入他的jar包，还要配置SpringMVC需要的配置。
第一步、导入jar包 Jackson Databind - maven
第二步、配置web.xml

附：阿里巴巴fastjson
<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.75</version>
</dependency>

```
一个Controller返回json格式的例子（完整版）
```java
//@Controller RestController的作用就是返回字符串，取代了下面的ResponseBody
@RestController
public class UserController {
    @RequestMapping(value = "/j1", produces = "application/json;charset=utf-8")
    //@ResponseBody //不会走视图解析器，会直接返回一个字符串
    public String json1() throws JsonProcessingException {
        //jackson中的对象：ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(1, "张三", "男");
        String str = mapper.writeValueAsString(user);
        return str;
    }

    @RequestMapping(value = "/j2")
    public String json2() throws JsonProcessingException {
        //jackson中的对象：ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(2, "李四", "男");
        String str = mapper.writeValueAsString(user);
        return str;
    }
    
    //这是返回一个集合的例子
    @RequestMapping(value = "/j3")
    public String json3() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<User> userList = new ArrayList<User>();
        User user1 = new User(1, "李1号", "男");
        User user2 = new User(2, "李2号", "男");
        User user3 = new User(3, "李3号", "男");
        User user4 = new User(4, "李4号", "男");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        String str = mapper.writeValueAsString(userList);
        return str;
    }

}
```
上面代码的几个问题
```text
第一个问题：需要解决乱码
    方法1：直接设置RequestMapping : (value ="/t1", produces = "application/json;charset=utf-8)
    方法2：SpringMVC配置中统一配置（在这一段的下方）。

第二个问题：我们需要返回的是一个json文本格式
    方法1：直接在RequestMapping下面使用@ResponseBody来让返回结果变为一个文本格式。
    方法2：将@Controller的注解变为@RestController，这也代表他是一个接口。

第三个问题：返回集合对象？
    List<User> userList = new ArrayList<User>(); userList.add()...
    return userList...
```
SpringMVC中对json乱码的配置（对jackson的，固定配置）
```xml
<mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
</mvc:annotation-driven>
```
题外：返回当前时间
```java
    //返回时间1
    @RequestMapping(value = "/j4")
    public String json4() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Date date = new Date();//这是返回时间戳Timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = mapper.writeValueAsString(sdf.format(date));
        return str;
    }

    //返回时间2
    @RequestMapping(value = "/j5")
    public String json5() throws JsonProcessingException {
        //1、创建Jackson对象
        ObjectMapper mapper = new ObjectMapper();
        //2、不适用时间戳的方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //3、自定义日期的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(sdf);
        //4、创建时间对象
        Date date = new Date();
        //5、返回
        return mapper.writeValueAsString(date);
    }
```
可以看到，Jackson的ObjectMapper对象重用我们可以写一个工具类代替。(重点：学会复用重载)
```java
public class JsonUtils {
    public static String getJson(Object object) {
        //学会复用！
        return getJson(object,"yyyy-MM-dd HH:mm:ss");
    }


    public static String getJson(Object object, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        //不适用时间戳的方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //自定义日期的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(sdf);

        //下面是返回值
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```
阿里巴巴的FastJson综合例子
```java
        System.out.println("***** Java对象 转换 Json字符串 *****");
        String str1 = JSON.toJSONString(userList);
        System.out.println("userList转换为了Json字符串 = " + str1);
        String str2 = JSON.toJSONString(user1);
        System.out.println("user1转换为Json字符串 = " + str2);

        System.out.println("\n***** Json字符串 转换 Java对象 *****");
        User jp_user1 = JSON.parseObject(str2,User.class);
        System.out.println("把前端Json变成了Java对象打印 = " + jp_user1);

        System.out.println("\n***** Java对象 转换 Json“对象” *****");
        JSONObject jsonObject1 = (JSONObject) JSON.toJSON(user2);
        System.out.println("user2被转换成了JSON对象，键值对name(名字)：" + jsonObject1.getString("name"));
        System.out.println("user2被转换成了JSON对象，键值对sex(性别)：" + jsonObject1.getString("sex"));

        System.out.println("\n***** JSON对象 转换 Java对象 *****");
        User to_java_user = JSON.toJavaObject(jsonObject1,User.class);
        System.out.println("把Json对象转回了Java对象 = " + to_java_user);
```