package com.ycsx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    /*添加视图Controller*/
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    /*通过继承后重写方法来拓展*/
    /*想定制化一些功能，只要写这个组件，然后交给springboot他就会帮我们自动装配*/
    @Bean
    public ViewResolver myViewResolver(){
        return new MyViewResolver();
    }

    public static class MyViewResolver implements ViewResolver{
        /*自定义了一个视图解析器*/
        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }

    /*自定义的国际化组件，略*/

    /*配置拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*excludePathPatterns()设置的参数是要过滤的参数，一个星号*代表一层过滤/x/y，**代表多层比如/a/b/c/d .. */
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html","/","/user/login","/css/*","/js/**","/img/**");

    }
}
