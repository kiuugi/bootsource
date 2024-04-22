package com.example.securyty;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {

    // SecurityConfig 의 passwordEncoder() 가 실행되면서 주입됨
    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화(encode), 원 비밀번호와 암호화된 비밀번호의 일치(matches) 여부

    @Test
    public void testEncoder() {
        String password = "1111"; // 원본 비밀번호

        String encodePassword = passwordEncoder.encode(password); // 비밀번호 암호화

        // password : 1111, encodePassword :
        // {bcrypt}$2a$10$D0bIQnQ0cIBMqqr.i/LPgO87TwgKlxcQRxKddIQglkI9.T0P5c1eC
        System.out.println("password : " + password + ", encodePassword : " + encodePassword);
        // {bcrypt} 암호화 알고리즘 중 하나
        // matches(원본 비밀번호, 암호화된 비밀번호) true
        // 단방향이기 때문에 비번을 암호화시켜서 비교함
        System.out.println(passwordEncoder.matches(password, encodePassword));
    }
}
