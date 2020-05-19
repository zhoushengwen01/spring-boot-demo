package com.example.configurtion;


import com.example.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor myInterceptor() {
        return new RequestInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 添加拦截路径
        // excludePathPatterns 排除拦截路径
        registry.addInterceptor(myInterceptor()).addPathPatterns("/**");
    }


}
