package com.beyond.basic.common.config;

import com.beyond.basic.common.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeRequests().antMatchers("/", "/author/create", "/author/login").permitAll().anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/author/login")
                    .loginProcessingUrl("/doLogin")             // 사전에 구성된 로그인 메서드
                    .usernameParameter("email")                 // 로그인 시 사용할 파라미터 이름
                    .passwordParameter("password")
                    .successHandler(loginSuccessHandler)
                .and()
                .logout().logoutUrl("/doLogout") // 사전에 구성된 로그아웃 메서드
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}