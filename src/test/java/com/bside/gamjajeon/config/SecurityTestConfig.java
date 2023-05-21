package com.bside.gamjajeon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityTestConfig {

    private final String[] permittedUrls = {"/v1/users", "/v1/users/login"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .rememberMe().disable()
                .formLogin().disable()

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, permittedUrls).permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}
