package com.ycsx.demo04_proxy;

import com.ycsx.demo03_proxy.Rent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* 万能代理的例子。一个动态代理类可以代理多个类。 */
public class ProxyInvocationHandler implements InvocationHandler {
    //第一部分：代理谁？
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    //第二部分：代理类
    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }

    //第三部分：代理实例的处理，并且返回结果。
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //使用method.getName() ==> 利用"反射"获取方法名。
        log(method.getName());
        //调用谁的方法？
        Object result = method.invoke(target, args);
        return result;
    }

    //使用代理来增加的功能。（不修改Dao持久层的代码。）
    public void log(String msg) {
        System.out.println("调用了" + msg + "方法。");
    }

}
