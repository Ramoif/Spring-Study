开始第一个Vue项目
```text
1、新建一个空项目，配置插件/导入Vue.js。
    插件没提示，我选择了cdn导入。
    <script src="http://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
2、编写程序（Vue-1/chapter-1/demo01.html）成功打印了Hello。

使用console改变vm.message的值动态改变显示内容，体现了双向绑定。
```
v-
```text
v-bind可以绑定一个悬停浮窗。
这种语法被称为指令。它们都带有v-。代表Vue提供的特殊特性。
它会在渲染的DOM上应用特殊的响应式行为。
```
第二个项目demo02
```text
本来是多个项目，但我都写到了demo02
判断：v-if v-else-if v-else
循环：v-for
方法：methods（只能定义在这个对象中），注意不是在data{}中。
绑定事件：v-on
```
Vue组件
```text
组件：可复用的实例。就是可以重复使用的模板。
在Script中定义了如下的变量，并且在data中设置items2的内容。
Vue.component("li01",{
     /*这里的props的名字要和下面{{}}中需要遍历的内容名对应。*/
     props:['prop'],
     template:'<li>{{prop}}</li>'
});
网页上展示内容，这里要理清v-bind和v-for的用法中这些属性名的对应的都是什么。
    <li01 v-for="show in items2" v-bind:prop="show"></li01>
```
第一个Axios项目（demo03）
```text
1、先准备一个Json文件，用于项目。（Vue-1/data.json）
2、创建一个新的项目（Vue-1/chapter-1/demo03.html）
3、引入js文件。
4、具体看实例。
```
计算属性computed
```text
计算属性，他是一个属性。这个属性有计算能力。计算就是个函数。
利用了虚拟DOM? 在内存中计算。
methods调用方法需要加上()，调用方法每次需要重新计算
computed调用方法不需要加上()，计算属性的主要特性就是将不经常变化的属性进行缓存。
具体看demo04的案例。
```
内容分发slot
```text
slot插槽，可以插入数据（个人认为是一个动态的套用数据的工具?）
案例见demo04的后半部分，单独的写了一个demo05。
注意v-for不要写成v:for

demo05使用了demo04的插槽，实现了不刷新页面删除li标签循环的元素。
删除事件->component->vue的方法->传参...
这个部分说的有点绕，详情见案例相关来看比较清楚。
```
第一个Vue-cli项目(刚刚开始vue)
```text
用于快速生成一个vue的项目模板。Nodejs的安装，见笔记3。
由于第一个项目的组件包过于庞大（100MB+），所以本项目没有上传相关代码。
```

Vue-Router 路由(需要Vue-cli，所以未展示)
```text
Vue-Router 路由
link用于跳转，view展示模板
vue-router是一个插件包，需要命令安装。
    npm install vue-router --save-dev (保存到开发配置里)
安装完以后使用import导入（.js）
    import VueRouter from 'vue-router'
显式声明使用vue-router
    Vue.use(VueRouter);
配置导出路由
    export default new VueRouter({
        routes:[{
        //路由路径
         path: ,
         name: ,
        //跳转的组件
         component: ,
        },{...},{...}]
    })
调用?(App.vue)
    <router-link to='/main'>首页</router-link>
    <router-view></router-view>
```
结合ElementUI组件库，创建一个工程
```text
一、初始化工程的流程，先cd到要创建的目录。
1、init webpack myvue    在这里创建一个myvue的文件夹作为工程文件夹。
2、Project name          默认名即可，下一步
3、Project description   描述，下一步
4、Author                输入名字
5、Vue build             选择standalone运行时编译
6、install vue-router    手动安装，n (No)
7、三个No
8、自动执行npm install   选择第三个，no
然后就看到有了新的文件夹。
9、cd进入myvue。安装依赖  npm install
10、安装完毕，报错就修复  npm audit fix
11、大约156Mb的文件夹，创建完毕。

二、安装依赖和插件
进入工程的目录（cd myvue）
1、vue-router        npm install vue-router --save-dev
2、element-ui        npm i element-ui -S
3、sass-loader       npm install sass-loader node-sass --save-dev
4、node-sass(↑)      npm install sass-loader node-sass --save-dev
5、启动测试          npm run dev

Npm命令解释
-g  安装到全局
-S  取代--save  安装到项目目录下，并且在package文件的dependencies节点写入依赖。
-D  --save-dev  安装到项目目录下，并且在...文件的devDependencies节点写入依赖。

然后open项目~
```
路由嵌套、参数传递、重定向、路由钩子
```text
待更新


```
登录模块代码
```html
<template>
  <div>
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit('loginForm')">登录</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>请输入账号和密码</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
```