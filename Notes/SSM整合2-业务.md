开始编写第一个业务-查询所有书籍，并且打印到前端页面
```text
框架已经大体搭建好了，开始编写业务。
也就是把Controller和jsp关联起来。

第一步：在controller下新建一个BookController.java，编写业务。
第二步：在WEB-INF下新建jsp文件，用来展示数据。
第三步：排错，开始美化页面。
    美化首页，在查询页面显示数据库数据。
    使用BootStrap美化。bootStrap可视化工具快速搭建。
    BootStrap 国内CDN库：直接添加到style
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    这里暂时使用一些简单的给jsp查询页面。对应的是jsp文件夹中的SSM-Build中的allBook.jsp。
第四步：排版前端页面，显示数据！第一步查询书籍功能基本大功告成。
```
第二个业务，增加书籍
```text
第一步：在页面增加新的功能按钮
第二步：在BookController增加新的请求页面跳转函数
第三步：添加add操作的实现函数
```
第三个业务，修改和删除
```text
第一步：前端页面中，在allBook页面增加操作功能的按钮，并且在forEach中也添加两个按钮
    &nbsp; => 在页面上显示为一个空格。
第二步：添加一个跳转到修改页面的RequestMapping和添加对应的更新页面。
第三步：实现点击修改操作的时候，携带数据显示原来的数据到对应的input标签内。
    通过页面的传递参数，在controller中get到前端的id，通过model来设置对应的数值。
    <input type="text" name="bookName" class="form-control" id="bkname" value="${QueryBooks.bookName}" required>
第四步：完成了上面的功能，开始实现修改页面提交后能够更新数据的功能。编写新的RequestMapping处理修改请求。

第五步：点击删除，编写一个controller函数和前端的删除a标签修改地址就行了。
```
第四个业务：查询功能
```text
写在前面：这里编写dao层的时候。没有写queryBookByName这个功能。
前端业务的访问是自上而下，我们业务代码应该提前规划好，dao层开始写。这样后来调用就不用来回的写业务。
这是一个很关键的设计理念。

第一步：查询书籍功能的添加，先修改当前页面布局...
    需要过？硬的前端排布，才能稍微好看点...
第二步：因为没有业务，所以从Dao->service->controller写缺失的功能。
第三步：我这里使用的是模糊查询，所以直接套用之前查询所有书籍的函数。返回的是List
第四步：添加一个空查询，用于显示所有书籍。（无内容查询全部）
    ps:因为我这里使用了模糊查询，就可以忽略这一步...
可以不用刷新来实现，使用Ajax。
到此，基础功能的实现大致上已经完成。
```
编写第一个查询业务整个过程中，个人出现的问题有如下
```text
1.启动不了tomcat服务器。
    这个问题是之前的lib文件夹没有创建导致的，在Artifact中引入需要jar包。

2.测试第一个查询业务逻辑的时候访问allBook.jsp报错500错误。
    排错的思路如下：
    1.问题是bean不存在。查看bean是否注入成功、
    2.Junit单元测试看是否能出现结果。
      test下创建一个测试类。经过了下面第三个问题，解决后，发现测试类可以实现查询全部图书的功能。
    3.都没有出错，那说明是spring出问题了。
      这时候尝试把BookController中引用的业务层换成如下，出现报错java.lang.NullPointerException
      private BookService bookService = new BookServiceImpl();
      但是肯定没有空指针的问题。肯定是Spring没有调用到。
    4.由此得出，整合的时候，SpringMVC没有调用到我们的Service层的Bean。
      1)applicationContext.xml 没有注入Bean
      2)web.xml中，我们也绑定过配置文件。我们配置的是Spring-mvc.xml中没有Service bean。
      这时候修改web.xml中。注意还原controller。
    此时问题2解决。
    
3.但在测试第二个错误的时候出现了问题：启动tomcat的时候报下面的错误
    java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
    这个报错异常。经过检查，是database.properties中的多余的空格引起的。但是我顺便也把&amp;删除了。不知道会不会有影响。
```
编写第二个业务-增加书籍出现的问题
```text
1.增加出现500错误。增加书籍的BookMapper.xml关于增加书籍的sql语句出错。因为id是自增并且前端传参只传递三个参数，所以修改后如下
    <insert id="addBook" parameterType="Books">
       insert into books(bookName, bookCounts, detail)
       values (#{bookName},#{bookCounts},#{detail});
    </insert>
2.解决页面不填写完就提交的报错问题。在input标签中添加required属性。强制非空提交。
```
编写第三个业务出现的问题
```text
1.500错误
    还是老问题，查找前端页面的跳转地址和后端代码是否出现问题。
2.点击修改发现没有生效。
    没有传递id，通过控制台输出可以排查到这个问题。
    还有一种可能是事务问题，但是提交可以成功，所以排除这个问题。
    通过在前端设置隐藏域来解决。<input type="hidden">
    控制台打印出了id，解决了问题。
    
3.问题2可以在mybatis中配置日志
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    这样每次执行sql，控制台都会输出日志信息。
```