package com.ycsx.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /*anon:无需验证
        * authc:需要认证
        * user:拥有 记住我 功能才能用
        * perms:拥有对某个资源的权限
        * role:拥有某个角色权限*/

        Map<String, String> filterMap = new LinkedHashMap<>();

        //授权权限的语句，和未授权页面配套，在这里设置权限访问
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");

        //登录和未登录的权限
        /*filterMap.put("/user/add","authc");
        filterMap.put("/user/update","anon");*/

        bean.setFilterChainDefinitionMap(filterMap);
        //未授权页面
        bean.setUnauthorizedUrl("/noauth");

        //设置登录请求
        bean.setLoginUrl("/toLogin");

        return bean;
    }

    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //关联UserRealm
        manager.setRealm(userRealm());
        return manager;
    }

    //Realm，需要自定义类，交给spring托管
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

}
