package com.ycsx.diy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//直接标注这是一个切面类
@Aspect
public class AnnotationPointCut {
    @Before("execution(* com.ycsx.service.UserServiceImpl.*(..))")
    public void before() {
        System.out.println("执行前注解---");
    }

    @After("execution(* com.ycsx.service.UserServiceImpl.*(..))")
    public void after() {
        System.out.println("执行后注解---");
    }

    @Around("execution(* com.ycsx.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("环绕前");
        //执行方法
        Object proceed = jp.proceed();
        System.out.println("环绕后");
    }
}
