package com.example.movie.service;

import com.example.movie.dto.MemberDto;
import com.example.movie.dto.PasswordChangeDto;
import com.example.movie.entity.Member;

public interface MovieUserService {
    // 회원가입
    String register(MemberDto insertDto) throws IllegalStateException;

    // 닉네임 수정
    void nicknameUpdate(MemberDto updateDto);

    // 비밀번호 수정
    void passwordUpdate(PasswordChangeDto pDto);

    // 회원탈퇴
    void leave(MemberDto LeaveMemberDto) throws IllegalStateException;

    // dto=> entity
    public default Member dtoToEntity(MemberDto dto) {

        return Member.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    // entity => dto
    public default MemberDto entityToDto(Member member) {

        return MemberDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .role(member.getRole())
                .build();
    }
}
