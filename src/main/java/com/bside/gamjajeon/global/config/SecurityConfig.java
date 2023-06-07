package com.bside.gamjajeon.global.config;

import com.bside.gamjajeon.global.security.filter.ExceptionHandlerFilter;
import com.bside.gamjajeon.global.security.filter.InitAuthenticationFilter;
import com.bside.gamjajeon.global.security.jwt.JwtUtil;
import com.bside.gamjajeon.global.security.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private static final String[] PERMITTED_URLS = {"/v1/users", "/v1/users/login", "/v1/users/check/username", "/v1/users/check/email"};
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final ObjectMapper mapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .rememberMe().disable()
                .formLogin().disable()

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, PERMITTED_URLS).permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(initAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter(), InitAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public InitAuthenticationFilter initAuthenticationFilter() {
        return new InitAuthenticationFilter(userDetailsService, jwtUtil);
    }

    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter(mapper);
    }

}
