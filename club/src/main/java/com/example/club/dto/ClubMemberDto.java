package com.example.club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubMemberDto {
    // 인증받은 값이 아닌 회원가입을 위한 DTO
    private String email;
    private String name;
    private boolean fromSocial;
    private String password;

}
