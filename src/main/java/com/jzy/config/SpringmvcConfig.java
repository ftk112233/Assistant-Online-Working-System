package com.jzy.config;

import com.jzy.web.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName SpringmvcConfig
 * @description 配置springmvc拦截器等
 * @date 2019/9/30 10:37
 **/
@Configuration
public class SpringmvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public UpdateSessionUserInfoInterceptor updateSessionUserInfoInterceptor(){ return new UpdateSessionUserInfoInterceptor(); }

    @Bean
    public ToolboxUploadCacheInterceptor toolboxUploadCacheInterceptor(){
        return new ToolboxUploadCacheInterceptor();
    }

    @Bean
    public TokenInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }

    @Bean
    public CsrfInterceptor csrfInterceptor(){
        return new CsrfInterceptor();
    }

    @Bean
    public EmailVerifyCodeInterceptor emailVerifyCodeInterceptor(){
        return new EmailVerifyCodeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //UpdateSessionUserInfoInterceptor
        registry.addInterceptor(updateSessionUserInfoInterceptor())
                .addPathPatterns("/index").addPathPatterns("/user/setInfo").addPathPatterns("/user/setEmail")
                .addPathPatterns("/user/setPhone").addPathPatterns("/user/setPassword").addPathPatterns("/user/admin/page"); //拦截项目中的哪些请求

        //ToolboxUploadCacheInterceptor
        registry.addInterceptor(toolboxUploadCacheInterceptor())
                .addPathPatterns("/index"); //拦截项目中的哪些请求

        //TokenInterceptor
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/**"); //拦截项目中的哪些请求

//        CsrfInterceptor
        registry.addInterceptor(csrfInterceptor())
                .addPathPatterns("/user/updateOwn*").addPathPatterns("/user/addNew*").addPathPatterns("/user/modifyCurrent*"); //拦截项目中的哪些请求

        //EmailVerifyCodeInterceptor
        registry.addInterceptor(emailVerifyCodeInterceptor())
                .addPathPatterns("/resetPassword").addPathPatterns("/user/modifyCurrentEmail"); //拦截项目中的哪些请求

    }
}
