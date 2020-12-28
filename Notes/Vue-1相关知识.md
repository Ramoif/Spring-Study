Vue
```text
引用自Kuangstudy博客。
Vue是一套用于构建用户界面的渐进式框架。被设计为可以自底向上逐层应用。
Vue的核心库只关注“视图层”。
```
笔记1专注于相关知识，以下是可能需要的信息
```html
Vue.js
<script src="http://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
Axios
<script src="http://unpkg.com/axios/dist/axios.min.js"></script>
```
前端和后端分离的时代，前端为主的MV*时代
```text
MVC（同步通信） Controller
MVP（异步通信）  Presenter
MVVM（异步通信） ViewModel
前端工作在浏览器端、后端工作在服务端。
相对独立部署，分工明确。
缺点是代码不能复用、全异步、性能并非最佳。
```
NodeJS带来了新的研发模式，JavaScript开始可以运行在服务端
```text
NodeJS是全栈模式。
需要前端对服务端编程有进一步认识，如TCP/IP等网络知识掌握。
前后分离的开发思想主要基于SoC（关注度分离原则）。
让前后端的职责更加清晰，分工更合理高效。
```
学习前的一些知识（看看就行）
前端三要素
```text
HTML + CSS + JavaScript
分别对应了 HTML（结构）、CSS（表现）、JS（行为）
```
CSS预处理器
```text
CSS预处理器定义了一种新的语言。为CSS增加了一些编程的特性。
“用一种专门的编程语言，进行Web页面的样式设计，再通过编译器转化为正常的CSS文件给项目使用。“
常见的CSS预处理器有：
SASS：基于Ruby。通过服务端处理。难。后台人员需要的话使用。
LESS：基于NodeJS。通过客户端处理。简单。实际开发中足够。
```
JavaScript（行为层）
```text
弱类型的脚本语言。
Native原生JS开发。ES标准发布到了ES9(2019截止)
ES6是常用的版本。ES5是全浏览器支持的。
View：JSP{{}}
```
JavaScript框架
```text
jQuery（简化了DOM操作，缺点是DOM操作太频繁）、
Angular（Google收购，提出了模块化开发的理念）、
React（FaceBook出品，提出了虚拟DOM的概念）、
Vue（综合了Angular和React的上面提出的两个优点）、
Axios（前端通信框架、Vue只关心视图层，不具备通信能力）
[虚拟DOM]：利用内存
```
UI框架
```text
Ant-Design：阿里巴巴出品，基于React
ElementUI、iview、ice：基于Vue
BootStrap：Twitter推出用于前端开发的开源工具包
AmazeUI：HTML5跨屏前端框架
```
JavaScript构建工具
```text
Babel：JS编译工具
WebPack：模块打包器
```
后端技术
```text
Express：NodeJS框架
Koa：Express简化版
NPM：类似于Maven
Yarn：类似于Maven和Gradle
```
Vue和MVVM
```text
Vue是MVVM模式的实现者。
MVVM是事件驱动编程方式。源于经典的MVC模式。
可以和视图层进行双向绑定数据。
它的核心就是实现了DOM监听、数据绑定。

MVVM模式和MVC模式一样，主要目的是分离视图和模型（View and Model）
低耦合：视图可以独立Model变化和修改。View和Model变化相互不影响。
可复用：视图逻辑在一个ViewModel中可以让很多View重用。
独立开发：分离业务逻辑和数据开发（ViewModel）和页面的设计。

Vue完全解耦了View和Model层。至关重要的解耦。
Vue.js轻量级，小。
移动优先，易上手。
```
Vue七大对象
```text
el、data、template、methods、render、computed、watch
```
什么是数据双向绑定（demo02）
```text
数据发生变化，视图也发生变化。
视图发生变化，数据也同步变化。
v-model 用来同步输入框的value。
```
Axios异步通信
```text
因为Vue是一个视图层框架。严格遵守SoC关注度分离原则。
需要网络通信，我们使用Axios。它主要作用就是实现Ajax异步通信。
基于ES6。需要修改IDEA中的JavaScript版本为ECMAScript 6。
```
Vue生命周期
```text
网页是经过渲染的。
我们的demo01例子，在审查元素中设置成slow 3G，你可以比较清楚的看到过程：
他是先经过了空白，然后出现了{{message}}，然后再变成了Hello，Vue！
这代表他不是一步完成的。
他经过了一个流程，这里大概概括。
实例化Vue对象，初始化事件，...，编译模板，...替换属性，销毁之前创建的hook钩子，...拆除组件，...
```