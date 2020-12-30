springboot装配原理
```text
SpringBoot（自动装配，run()方法，接管SpringMVC的配置，实操）
p6-p19  p20-p30  p31-p37  p38-p45  p46-p53  p54-p61
```
pom.xml中的依赖
```text
spring-boot-starter 启动器
就是springboot的启动场景。
比如spring-boot-starter-web，他就会自动导入web环境的所有依赖。
springboot会将所有的功能场景都变成一个个的启动器。
```
自动配置原理(重点)_编辑2次
```text
逐层的图示：
@SpringBootApplication
    @SpringBootConfiguration
        @Configuration
            @Component
    @EnableAutoConfiguration //自动导入包
        @AutoConfigurationPackage
            @Import(AutoConfigurationPackages.Registrar.class)  //自动注册包
        @Import(AutoConfigurationImportSelector.class) //自动导入包的核心
            AutoConfigurationImportSelector.class  //自动导入选择器（选择了什么）
                getAutoConfigurationEntry()  //获得自动配置的实体
                getCandidateConfiguration()  //获得候选的配置
                loadFactoryNames()  //获取所有的加载配置
                loadSpringFactories
                    classLoader.getResources(FACTORIES_RESOURCE_LOCATION)  //项目资源
                    classLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION)  //系统资源
                    从这些资源中遍历了nextElement(自动配置)，遍历完成后封装成properties供使用。
    @ComponentScan //扫描包

FACTORIES_RESOURCE_LOCATION = "META_INF/spring.factories" //从这里获取配置
spring-boot-autoconfigure-2.2.0.RELEASE.jar // ↑ 配置在这个包内。版本可变

打开配置文件，可以看到许多没有被加载（注解掉了）。
是因为有一个@ConditionalOnXXX的注解，他会判断条件是否生效来进行导入。
例如@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})

所有的配置都是在启动的时候扫描并且加载。只要导入了对应的start启动器，自动配置就会生效。
springboot启动的时候会从spring.factories获取指定的值，并且把这些自动配置的类导入容器，自动配置就会生效，自动配置。
以前需要手动配置的东西，springboot来完成了。
整合javaEE，解决方案和自动配置都在spring-boot-autoconfigure-2.2.0.RELEASE.jar包下。
他会把所有需要导入的组件，以类名的方式返回，这些组件就会被添加到容器中。
容器中存在非常多的AutoConfiguration后缀文件，这些类就是给容器中导入了场景所需要的组件，并且自动配置。
    @Configuration，JavaConfig
有了自动配置类，省去了手动配置。

再次编辑复习：
打开配置文件，随便点开一个（ctrl+左键）发现都有一些共有的注解。
@Configuration  
    代表这是一个配置类
@EnableConfigurationProperties(prefix = "")  
    自动配置属性，可以设置类中的属性。
@Conditional/OnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Conditional/OnClass(characterEncodingFilter.class)
@Conditional/OnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true)
    根据不同的条件，来判断当前配置或者类是否生效。
    他有很多的分类：
        OnJava          系统的Java版本是否符合要求，不符合则不生效
        OnBean          容器中存在Bean，不存在则不生效
        OnExpression    满足SpEL表达式
        OnResource      类路径下是否存在指定资源文件
        OnClass         系统中有指定的类
        OnMissingClass  系统中没有指定的类
        OnProperty      系统中是否有指定的值

☆配置文件中能配置的东西都有一个固有的规律
[...]AutoConfiguration: 默认值     //向容器中自动配置组件
[...]Properties 和配置文件绑定      //自动配置类，装配配置文件中一些自定义的内容

我们就可以使用自定义的配置了。

举例server.port
通过在application.yaml按住ctrl查看port，发现点开了一个ServerProperties的类。
他有一个私有属性port对应了我们点击的port。

在application.yaml中配置debug: true可以看到，控制台输出有三种信息：
Positive matches：已经激活的自动配置类
Negative matches：没有激活/生效的自动配置类
Exclusions：none
Unconditional classes：没有条件的类
```
SpringApplication.run
```text
一、构造函数。SpringApplication这个类做了以下四件事情
* 1.推断应用的类型是普通项目还是Web项目。
* 2.查找并且加载所有的可用初始化器(?)，设置到initializers属性中
* 3.找出所有的应用程序监听器，设置到listeners属性中
* 4.推断并设置main方法的定义类，找到运行的主类

二、实例对象 .run()
```
第二个项目(springboot-demo02)
```text
创建的时候选择springWeb项目，然后删除多余的文件。
springboot的application.properties文件替换为application.yaml
```
SpringBoot全局配置文件
```text
配置文件名称固定。
1、application.properties
    语法结构 key=value
2、application.yml
    语法结构 key:(空格)value == key: value
```
yaml语法
```text
SpringBoot 推荐使用yaml。

#普通写法，空格很关键
name: zhangsan

#对象
student:
  name: zhangsan
  age: 18

#行内写法
student2: {name: zhangsan,age: 18}

#数组
pets:
  - cat
  - dog
  - pig

#数组行内写法
pets2: [cat,dog,pig]
```
使用yaml来注入pojo类
```text
首先需要把Person类加上@ConfigurationProperties(prefix = "person")
并且在application.yaml中添加信息：
```
```yaml
#注入一个person对象
person:
  name: zhangsan
  age: 3
  happy: false
  birth: 2020/12/29
  maps: {k1: v1,k2: v2}
  list:
    - code
    - music
    - girl
  dog:
    name: 旺财
    age: 3
```
```text
写一个测试类：测试打印如下：
Dog{name='小狗', age=3}

Person{
    name='zhangsan', 
    age=3, 
    happy=false, 
    birth=Tue Dec 29 00:00:00 CST 2020, 
    dog=Dog{name='旺财', age=3}, 
    lists=null, 
    maps={k1=v1, k2=v2}
}

发现list和lists两个没有对应，所以失败了。
修改相关参数重新打印：
lists=[code, music, girl]
```
@ConfigurationProperties()
```text
报红的，添加pom.xml依赖
```
```xml
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-configuration-processor</artifactId>
     <optional>true</optional>
</dependency>
```
```text
这个注解的作用就是把配置文件中的配置的每一个属性的值，映射到这个组件中。
告诉SpringBoot把本类所有属性和配置文件中相关的配置进行绑定。
参数prefix = "person" == 把配置文件中的person和下面的所有属性一一对应。
只有这个组件是容器中的组建才能使用容器提供的@ConfigurationProperties功能。
```
使用Properties
```text
创建一个xxx.properties
ps:如果使用Properties，注意将IDEA中的file encodings修改为UTF-8。（我默认为GBK）

然后在Person类上添加注解
@PropertySource(value = "classpath:xx.properties")
然后在属性上添加@Value
    @Value("${name}")
    private String name;
```
yaml的其他用法
```yaml
#可以拼接属性，使用${}
person:
  #可以使用${random.int}随机数 拼接属性值
  name: zhangsan${random.int}
  age: ${random.int}
  happy: false
  birth: 2020/12/29
  maps: {k1: v1,k2: v2}
  lists:
    - code
    - music
    - girl
  dog:
    #如果存在hello则用hello属性值，如果不存在则为hello(后面的值)
    name: ${person.hello:hello}_旺财
    age: 3
```
#### @ConfigurationProperties 和 @Value 区别

| 功能 | 批量注入 | 一个个指定 |
| :-: | :-: | :-: |
| 松散语法/绑定：| 支持 | 不支持 |
| SpEL | 不支持 | 支持 |
| JSR303数据校验 |支持 | 不支持 |
| 复杂类型封装 | 支持 | 不支持 |

上面的一些功能意思
```text
松散绑定：
    比如yaml中写last-name，Person中LastName可以对应上。-后面默认大写。

JSR303数据校验：
    可以再字段增加一层过滤器验证。保证数据的合法性。
    @Validated //数据校验注解
    加了这个注解以后，我们在name上添加一个@Email()的注解。
    运行测试类发现报错了，提示Reason：不是一个合法的电子邮件地址。
    可以添加属性(message="格式错误)
    这里举例一些注解：
        @Null
        @NotNull
        @AssertTrue  必须为True
        @Min(value)  必须是一个数字，大于等于指定的最小值
        @Max(value)
        @DecimalMin(value)  必须是一个数字。大于等于指定的最小值。
        @Size(max,min)      必须在范围内
        @Digits(integer, fraction)  必须是一个数字，必须在可以接受的范围内
        @Past  必须是过去的日期
        @Future
        [☆] @Pattern(value)   必须符合正则表达式
        @Email
        @Length
        @NotEmpty
        @Range

复杂类型封装：
    yaml可以封装对象。
```
多环境配置
```text
可以在四个位置（2.1.9版本）创建application的配置文件。
1.根目录/
2.根目录/config/
3.resources/
4.resources/config/

优先级*（v2.4.1）：我设置了四个端口，成功一个删除一个配置：
1、8084 (=>4.)
2、8083 (=>3.)
3、8080 (都没生效)

于是我查找了2.4.1的springboot文档：（机翻）
    如果指定了多个位置，则后面的位置可以覆盖前面的位置。

    如果您希望添加其他位置，而不是替换它们，则可以使用spring.config.additional-location。从其他位置加载的属性可以覆盖默认位置的属性。
    例如，如果spring.config.additional-location使用value进行配置，则所optional:classpath:/custom-config/,
    optional:file:./custom-config/考虑的位置的完整集合为：
    1.classpath:/
    2.classpath:/config/
    3.file:./
    4.file:./config/
    5.file:./config/*/
    6.classpath:custom-config/
    7.file:./custom-config/
    通过此搜索顺序，您可以在一个配置文件中指定默认值，
    然后在另一个配置文件中有选择地覆盖这些值。
    您可以在以下默认位置之一为您的应用程序提供默认值application.properties（或您选择的任何其他基本名称spring.config.name）。
    然后，可以在运行时使用自定义位置之一中的其他文件覆盖这些默认值。

反正这里知道了不同版本的是会有区别的。
```
指定多环境配置(原文用的properties，但我使用了yml格式)
```yaml
#多环境配置，这么配置他就会走application-test.properties的配置
spring:
  profiles:
    active: test
```
yml独有的方式
```yaml
#这里指定
server:
  port: 8091
spring:
  profiles:
    active: dev

---
server:
  port: 8092
spring:
  profiles: dev

---
server:
  port: 8093
spring:
  profiles: test
```
```text
启动测试，发现他走的是8092，测试成功。
Tomcat initialized with port(s): 8092 (http)
然后在demo02下我删除了测试所用的application.yaml，为了测试其他的类。yaml配置大部分已经放到了上面。
```