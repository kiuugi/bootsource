package com.example.club.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ClubAuthMemberDto extends User {

    // db에서 인증된 정보를 담을 객체

    private String email;
    private String name;
    private boolean fromSocial;

    public ClubAuthMemberDto(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        // auth 로 봤던 정보를 dto로 옮기는 작업
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;
    }

}
