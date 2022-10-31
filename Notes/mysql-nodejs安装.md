# Mysql5.7.31

第一步，解压缩

第二步，配置环境变量

> 在环境变量中添加MYSQL_HOME，路径选择安装路径；
>
> 在Path中添加%MYSQL_HOME%\bin，注意Path中不同值之间的"；"符号不能省略。

第三步，以管理员方式打开CMD

> 进入mysql的bin目录，执行mysqld install

第四步，初始化

>  mysqld --initialize-insecure --user=mysql 
>
> 执行完会帮你创建一个data文件夹。
>
> 但是我是copy的原电脑的mysql全部文件，我可以直接进行最后一步登录了，账户密码全部无误。

第五步，启动mysql

> net start mysql
>
> net stop mysql

第六步，修改密码

> 修改密码
>
> ```shell
> UPDATE mysql.user SET authentication_string = PASSWORD('新密码') WHERE user = 'root';
> # 我的命令 UPDATE mysql.user SET authentication_string = PASSWORD('123456') WHERE user = 'root';
> FLUSH PRIVILEGES;
> ```
>
>  注意：5.7已经取消了password字段，务必写authentication_string 



# Nodejs安装

> 直接官网下载msi文件，全程下一步。
>
> 尝试直接兼容一下vue项目。

改变路径

> 在nodejs目录下新建node_cache和node_global
>
> **然后cmd执行命令：**注意此处双引号，不然会修改错误路径
>
> npm config set prefix “G:\nodejs\node_global”
>
> npm config set cache ”G:\nodejs\node_cache“
>
> **然后修改环境变量（在用户变量中）**
>
> Path中的（以我举例）C:\Users\Ekagac\AppData\Roaming\npm
>
> 改为G:\nodejs\node_global
>
> **在系统变量中新建NODE_PATH**
>
> 路径为G:\nodejs\node_global\node_modules
>
> **在系统变量的PATH中新增**
>
> G:\nodejs\node_global\node_modules
>
> **更换为淘宝镜像**
>
>  npm config set registry https://registry.npm.taobao.org/ 
>
> 查看配置是否成功
>
> npm config get registry

安装cnpm

>  npm install -g cnpm --registry=https://registry.npm.taobao.org 



nodejs版本不同导致的sass报错解决方法：

> 先卸载npm uninstall --save node-sass
>
> npm i -D sass



# Python

先下载python安装包安装。https://www.python.org/

直接Download-windows下载执行文件安装。

**安装两个插件**：flake8检查不规范和语法错误；yapf代码格式化工具。

打开cmd输入`pip install flake8`和`pip install yapf`即可。

在.vscode文件夹下的settings.json中输入下面内容

```json
{
    "python.formatting.provider": "yapf",
    "python.linting.flake8Enabled": true,
    "python.linting.flake8Args": [
        "--max-line-length=248"
    ],
    "python.linting.pylintEnabled": false
}
```

按alt+shift+f就可以规范代码了。















