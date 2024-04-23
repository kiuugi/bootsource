package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.club.handler.ClubLoginSuccessHandler;

@EnableMethodSecurity // @PreAuthrize 활성화
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {

        http
                // .authorizeHttpRequests( // 전부 명시해서 적는 방법
                // authorize -> authorize.requestMatchers("/", "/auth", "/static/**",
                // "/img/*").permitAll()
                // // 사실 사용자에서 admin, manager, user를 다 부여해놔서 상관없음
                // .requestMatchers("/club/member").hasAnyRole("USER", "MANAGER", "ADMIN")
                // requestMatchers 됬을때 hasAnyRole 무슨 Role이 필요한지
                // .requestMatchers("/club/manager").hasAnyRole("MANAGER")
                // .requestMatchers("/club/admin").hasAnyRole("ADMIN"))
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()) // 일단 모든 Request를 permit 허용하고, controller에서 permit을 명시해
                // 지금은 controller에서 PreAuthorize를 줘서 따로 명시함
                .formLogin(login -> login.loginPage("/club/member/login").permitAll()
                        .successHandler(clubLoginSuccessHandler()))
                .oauth2Login(login -> login.successHandler(clubLoginSuccessHandler()))
                .rememberMe(remember -> remember.rememberMeServices(rememberMeServices))
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/club/member/logout"))
                        .logoutSuccessUrl("/"));
        return http.build(); // Exception 날리기 / 여기가 설정창이니까 설정한 정보를 http에 담아서 넘기는듯
    }

    // 암호화(encode) 작업, 비밀번호 입력값 검증(matches) : PasswordEncoder
    // 단방향 암호화 : 암호화만 가능하고 복호화는 불가능 decoding이 불가능함
    // 비밀번호를 까먹었을때 원래 있는 비밀번호를 찾아주는게 아니라 새로 만드는 이유
    @Bean // 객체 생성
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean // 스프링컨트롤러가 관리해햐하는객체
    ClubLoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubLoginSuccessHandler();
    }

    // 자동 로그인 처리 - 1)쿠키 이용 2)데이터베이스 이용
    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        // RememberMeTokenAlgorithm 브라우저에 저장하기 위해 쿠키도 암호화시킴
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("mykey", userDetailsService,
                encodingAlgorithm); // TokenBasedRememberMeServices : 쿠키를 이용할때 쓰기 편하게 만들어둔 객체
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7); // 쿠키 만료시간(지금 설정한 시간은 7일정도)
        return rememberMeServices;
    }
}
