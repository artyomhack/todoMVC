package com.artyomhack.todo.config;

import com.artyomhack.todo.security.CustomUserDetailService;
import com.artyomhack.todo.service.user.UserService;
import com.artyomhack.todo.service.user.UserServiceImpl;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("menu");
    }
}
