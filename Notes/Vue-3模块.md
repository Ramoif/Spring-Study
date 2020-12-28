NodeJs
```text
在中文网安装。查看版本：node -v  ；npm -v  。
然后CMD运行下载组件的指令：npm install cnpm -g
然后 cnpm install vue-cli -g
这两个下载完就可以创建第一个vue-cli项目了。
在C:\Users\[YourName]\AppData\Roaming\npm下会出现下载的所需要的组件。
```
CommonsJS、AMD、CMD、还有其他规范
```text
服务器端的NodeJS遵循这个CommonsJS规范。
他的思想是允许模块通过require方法同步加载所需依赖的其他模块。然后exports或者module.exports导出需要暴露的接口。
require("module");
require("../module.js");
export.doStuff = function() {};
module.exports = someValue;

优点：服务器端便于重用。
NPM中有45W+个可以使用的模块包。
简单易用。

缺点：同步的模块加载方式不适合在游览器中，同步意味着阻塞加载。浏览器是异步加载。
不能非阻塞的并行加载多个模块。
```
ES6模块
```text
import "jquery";
export function doStuff() {}
module "localModule" {}

优点：容易进行静态分析。
面向未来的EcmaScript标准。

缺点：原生浏览器端还没实现。
新版的NodeJS才支持全新命令。
```
Webpack与它的安装、配置
```text
webpack是一个现代JavaScript的静态模块打包器。
现在越来越多的网站使用WebApp模式。使用HTML5，CSS3，ES6新技术来丰富功能。
WebApp通常是一个单页面应用(SPA)。

WebPack它能够把各种的资源（JS、JSX、ES6、SASS、LESS、图片）都当做模块来处理使用。

安装WebPack：（这里使用cnpm会更快）
npm install webpack -g
npm install webpack-cli -g

测试安装成功：
webpack -v
webpack-cli -v

配置：
webpack.config.js
```
创建一个项目使用webpack
```text
1、创建项目（空文件夹）
2、新建一个文件夹modules
3、代码见Vue-2-webpack
4、配置webpack.config.js，然后运行打包命令'webpack'
5、打包以后文件夹下会出现dist文件夹。

摘自其他博客的一些配置解决问题：
1、webpack.config.js的问题  https://blog.csdn.net/zinbin/article/details/106325164
如果是 v4.0.0 以上版本的 webpack，可以不使用配置文件配置 webpack，使用官网推荐的使用 ./src/index.js 作为入口点。
2、这个项目的配置文件应该是需要放到根目录的。所以这个笔记项目无法打包。
```
