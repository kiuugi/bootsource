package com.example.board.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

// 회원 정보 + 허가와 관련된 정보(접근 권한)

@Data
public class MemberAuthDto extends User {

    private MemberDto memberDto;

    // Collection 구현한애들 List(ArryList,...), set(HashSet)
    public MemberAuthDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        // Collection<? extends GrantedAuthority> : GrantedAuthority를 포함, 구현, 상속한 타입만 가능
        super(username, password, authorities);

    }

    public MemberAuthDto(MemberDto memberDto) {
        super(memberDto.getEmail(), memberDto.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDto.getMemberRole())));
        this.memberDto = memberDto;
    }

}
