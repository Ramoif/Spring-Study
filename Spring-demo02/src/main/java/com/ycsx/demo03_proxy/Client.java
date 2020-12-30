package com.ycsx.demo03_proxy;

public class Client {
    public static void main(String[] args) {
        /*真是角色*/
        Host host = new Host();
        /*代理角色*/
        ProxyInvocationHandler pih = new ProxyInvocationHandler();
        pih.setRent(host);

        Rent proxy = (Rent) pih.getProxy();
        proxy.rent();
    }
}
