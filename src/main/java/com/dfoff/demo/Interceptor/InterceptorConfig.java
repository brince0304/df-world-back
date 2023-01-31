package com.dfoff.demo.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
        private final BoardInterceptor boardInterceptor;

    public InterceptorConfig(@Autowired BoardInterceptor boardInterceptor) {
        this.boardInterceptor = boardInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(boardInterceptor)
                .addPathPatterns("/boards")
                .addPathPatterns("/boards/insert")
                .addPathPatterns("/boards/characters/")
                .addPathPatterns("/hashtags/");
    }
}
