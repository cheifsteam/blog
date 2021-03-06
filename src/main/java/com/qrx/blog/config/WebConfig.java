package com.qrx.blog.config;

//import com.hqd.schoolnavigation.util.handler.UserSecurityHandlerInterceptor;
//import com.hqd.schoolnavigation.util.handler.UserSecurityHandlerInterceptor;

import com.qrx.blog.constant.Constants;
import com.qrx.blog.util.handler.UserSecurityHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            //重写这个方法，添加跨域设置
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //定义哪些URL接受跨域
                registry.addMapping("/**")
                        //定义哪些origin可以跨域请求
                        .allowedOriginPatterns("*")
                        //定义接受的跨域请求方法
                        .allowedMethods("POST", "GET", "PUT", "PATCH", "OPTIONS", "DELETE")
                        .exposedHeaders("Set-Token")
                        .allowCredentials(true)
                        .allowedHeaders("*")
                        .maxAge(3600);
            }
            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                String[] addPath={
                        "/admin/admin/updateNickname"
                };
                String[] excPath={
                        "/admin/admin/login","/web/user/login","/web/school/getAll","/web/school/getLike","/web/user/register","/admin/user/getAll"
                };
                registry.addInterceptor(userSecurityHandlerInterceptor()).addPathPatterns(addPath).excludePathPatterns();
            }
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.RESOURCE_WIN_PATH);
            }
        };

    }
    //定义拦截器，UserSecurityHandlerInterceptor这个类实现了HandlerInterceptor接口
    @Bean
    public UserSecurityHandlerInterceptor userSecurityHandlerInterceptor() {
        return new UserSecurityHandlerInterceptor();
    }



}

