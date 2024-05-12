package com.example.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.movie.handler.CustomAccessDeniedHandler;

@EnableMethodSecurity // @PreAuthrize 활성화
@EnableWebSecurity // web에서 security 부분을 담당함을 알림
@Configuration // 환경설절파일임을 알림
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/assets/**", "/css/**", "/js/**", "/auth").permitAll()
                .requestMatchers("/movie/list", "/movie/read", "/upload/display", "/reviews/**").permitAll()
                .requestMatchers("/movie/modify").hasRole("ADMIN")
                .requestMatchers("/member/register").permitAll()
                .anyRequest().authenticated())
                // login 페이지는 /member/login 경로 요청 해야한다는 의미
                // login 성공 후 이동경로는 시작했던 페이지가 기본
                .formLogin(login -> login.loginPage("/member/login").permitAll()
                        .defaultSuccessUrl("/movie/list", true))
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/"));
        // http.csrf(csrf -> csrf.disable()); // csrf 필터 비활성화
        // get을 제외한 모든 방은 csrf 토큰이 필요해짐
        // => thymeleaf 에서 form:action 을 삽입하면 자동으로 만들어줌
        // => 403 발생시 1차적으로 csrf 토큰이 포함되었는지 확인

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        // 403 접근제한 - 정적 페이지와 연결
        // http.exceptionHandling(exception ->
        // exception.accessDeniedPage("/access-denied.html"));

        // 접근제한 - 좀 더 다양한 작업을 할 때
        http.exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler()));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
