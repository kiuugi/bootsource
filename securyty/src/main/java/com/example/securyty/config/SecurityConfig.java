package com.example.securyty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // 웹에서 security 적용 가능
@Configuration // == @Component(@Controller, @Service) : 객체(환경설정용객체) 생성
public class SecurityConfig {

    // 접근제한 개념

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 요청 확인
        http.authorizeHttpRequests(authorize -> authorize
                // request 요청 페이지()에 대한 permit 퍼미션을 준다.
                .requestMatchers("/", "/security/guest", "/auth").permitAll()
                .requestMatchers("/security/member").hasRole("USER")
                .requestMatchers("/security/admin").hasRole("ADMIN"))

                // 인증 처리(웹에서는 대부분 폼 로그인 작업)
                // .formLogin(Customizer.withDefaults()); // default 로그인 페이지 보여주기
                .formLogin(login -> login.loginPage("/member/login").permitAll()) // custion login page 사용
                // .usernameParameter("userid") username 이름 변경시
                // .passwordParameter("pwd") password 요소 이름 변경시
                // .successForwardUrl("/") 로그인 성공 후 가야할 곳 지정

                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/") // default 로그인 페이지임
                ); // custiomlogout

        return http.build(); // throws Exception
        // 지금부터 securityFilter를 검
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 임시 - 데이터베이스에 인증을 요청하는 객체(지금 DB연동을 안해놓음)
    // InMemoryUserDetailsManager - 메모리에 등록해 놓고 임시로 사용
    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder().username("user1")
                .password("{bcrypt}$2a$10$D0bIQnQ0cIBMqqr.i/LPgO87TwgKlxcQRxKddIQglkI9.T0P5c1eC").roles("USER").build();

        UserDetails admin = User.builder().username("admin1")
                .password("{bcrypt}$2a$10$D0bIQnQ0cIBMqqr.i/LPgO87TwgKlxcQRxKddIQglkI9.T0P5c1eC").roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);

    }

}
