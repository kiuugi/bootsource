package com.example.club.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ClubUserDetailService implements UserDetailsService {
    // auth 쓰고 봤던 그 정보들을 담는 객체
    // UserDetails <- User(impl/UserDetails 구현) <- CustomUser(User 상속)
    // 지금은(ClubAuthMemberDto)

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 담당 메소드
        // UserDetails를 return 해야 하는데 UserDetails는 인터페이스이기 때문에 UserDetails는를 구현하고있는 다른
        // 클래스를 리턴함
        log.info("로그인 요청 {}", username);
        return null;
    }

}
