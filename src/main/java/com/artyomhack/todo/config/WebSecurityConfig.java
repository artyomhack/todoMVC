package com.artyomhack.todo.config;

import com.artyomhack.todo.entity.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request) -> request
                        .anyRequest().authenticated()
                );

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder.encode("passwordHash"))
//                .roles(Role.USER.getRole())
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }



}
