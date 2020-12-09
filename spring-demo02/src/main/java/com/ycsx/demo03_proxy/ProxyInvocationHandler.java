package com.ycsx.demo03_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/* 自动代理 ==> 这个类就是动态代理的实现类(?) */
public class ProxyInvocationHandler implements InvocationHandler {
    private Rent rent;

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public Object getProxy(){
        //getClassLoader ==> 类加载器。
        //参数含义：1.加载类在哪个位置？  2.要代理的接口是哪一个？ 3.代表自己的InvocationHandler。
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), rent.getClass().getInterfaces(),this);
    }
    /* 处理代理类，返回结果 */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /*动态代理的本质，就是使用"☆反射"的机制。反射可以动态的获取类的一些方法(?)*/
        seeHouse();
        Object result = method.invoke(rent, args);
        return null;
    }

    public void seeHouse(){
        System.out.println("看房子。");
    }
}
