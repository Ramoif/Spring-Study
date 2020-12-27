Ajax
```text
引用自kuangstudy博客：
Ajax = Asychronous JavaScript and XML 异步的JavaScript 和 XML
他是一种无需重新加载整个网页的情况下，更新部分网页的技术。
举个例子 在百度输入框中输入信息，下方自动联想相关关键词。
```
复习一遍搭建基础流程
```text
第一步：新创建了一个Module（SpringMVC-demo06-ajax）
第二步：从头搭建项目的框架（复习，需要熟练）
    1.添加网页框架
    2.web.xml中配置DispatcherServlet和Filter
    3.把java下的包创建好。
    4.编写Dispatcher需求的applicationContext.xml（三(4)个核心：注解驱动和自动扫描controller包、静态资源过滤、视图解析器）
    5.写一个RestController测试一下。
第三步：开始写其他...
```
jQuery实现Ajax (/a1)
```text
jQuery提供了多种ajax方法。jQuery是一个库。
调用jQuery的两种方法
1.jQuery
2.cdn
一个失去焦点的例子的script部分：（index.jsp）
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.5.1.js"></script>
    <script>
      function a(){
        jQuery.post({
          url:"${pageContext.request.contextPath}/a1",
          /*注意这里键值对获取元素的写法。*/
          data:{"name":$("#username").val()},
          success:function (data) {
            alert(data);
          }
        })
      }
    </script>
```
Ajax异步加载数据 (/a2)
```text
笔记待更新
```
拦截器
```text
SpringMVC框架才有拦截器。只有使用他的框架才能使用。
（AOP思想？）需要配置AOP。
拦截器类似于Servlet开发中的过滤器Filter。用于对处理器进行预处理和后处理。
拦截器只会拦截访问的控制器方法。如果访问的是jsp html css image js是不会进行拦截的。
实现拦截器只需要实现HandlerInterceptor接口来自定义拦截器。

他有什么用？ 例子：未登录的用户拦截登录。
笔记待更新。
```
文件上传和下载
```text
待更新
```
SSM-阶段总结
```text
1、回顾Mybatis、Spring、SpringMVC都是做什么的。
Mybatis：持久化、增删改查、配置、
    [☆重点]结果集映射ResultMap(需要回顾)、
    Log4j、分页、注解开发、动态Sql(4个)、缓存。
Spring：两个核心 IOC(控制反转)、AOP(面向切面编程)
    [☆重点]DI-Set注入 / c p 命名空间、
    [☆重点]代理模式(静态、动态)
    使用注解开发Spring、
    [☆半重点]纯JavaConfig、整合Mybatis：事务、声明式事务。
SpringMVC：[☆重点]SpringMVC的执行流程、
    执行原理：三大原理（处理器、适配器(前两种被注解取代)、视图解析器）、
    结果跳转的方式（重定向、转发）、数据处理方式（字段名、对象）、乱码问题、Controller、
    [☆重点]RestFul风格、
    [☆重点]整合SSM框架的项目。
    JSON、Ajax、拦截器。
```