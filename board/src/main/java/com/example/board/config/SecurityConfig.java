package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity // @PreAuthrize 활성화
@EnableWebSecurity // web에서 security 부분을 담당함을 알림
@Configuration // 환경설절파일임을 알림
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/static/**", "/css/*", "/assets/*", "/img/*",
                // "/js/*").permitAll()
                .requestMatchers("/board/read").permitAll()
                .requestMatchers("/board/modify").authenticated()
                .anyRequest().permitAll()) // authenticated()) // 모든 리퀘스트 인증받기
                .formLogin(login -> login.loginPage("/member/login").permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
