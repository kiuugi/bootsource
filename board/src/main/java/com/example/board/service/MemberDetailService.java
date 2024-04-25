package com.example.board.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.constant.MemberRole;
import com.example.board.dto.MemberAuthDto;
import com.example.board.dto.MemberDto;
import com.example.board.entity.Member;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService, MemberService {
    // 로그인작업하는 정해진 클래스
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // id == email == username
        Optional<Member> result = memberRepository.findById(username);

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("이메일을 확인해 주세요");
        }
        Member member = result.get();
        // entity => dto
        // security 로그인 ==> 회원 정보 + 허가와 관련된 정보(사이트 접근 권한)
        MemberDto memberDto = MemberDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .memberRole(member.getMemberRole())
                .build();

        return new MemberAuthDto(memberDto);
    }

    @Override
    public void register(MemberDto insertDto) throws IllegalStateException {

        validateDuplicationMember(insertDto.getEmail());
        // try {
        // 중복 이메일 검사 /중복으로 들어오면 save 할 때 insert가 아니라 update가 나음
        // repository.save() => select 존재시 update, 없으면 insert
        Member member = Member.builder()
                .email(insertDto.getEmail())
                .password(passwordEncoder.encode(insertDto.getPassword()))
                .name(insertDto.getName())
                .memberRole(MemberRole.MEMBER) // 현재 회원가입으로 가질 수 있는 권한은 MEMBER밖에없음
                .build();

        memberRepository.save(member);
        // } catch (Exception e) {
        // throw e;
        // }
    }

    // 애초에 회원 가입DB에 넣지도 안았는데 find 돌렸더니 나오면 중복이지
    private void validateDuplicationMember(String email) {
        Optional<Member> member = memberRepository.findById(email);
        if (member.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}
