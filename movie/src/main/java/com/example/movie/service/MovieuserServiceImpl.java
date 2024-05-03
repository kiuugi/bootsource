package com.example.movie.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movie.constant.MemberRole;
import com.example.movie.dto.AuthMemberDto;
import com.example.movie.dto.MemberDto;
import com.example.movie.dto.PasswordChangeDto;
import com.example.movie.entity.Member;
import com.example.movie.repository.MemberRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieuserServiceImpl implements UserDetailsService, MovieUserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 요청 {}", username); // username을 못찾으면 UsernameNotFoundException이걸 날림

        // security에서 사용하는 로그인 메소드
        // UserDetails 을 구현한 클래스 User , 를 상속받는 CustomUser
        // Optional<Member> result = memberRepository.findById(null);
        // if (result.isPresent()) {
        // Member member = result.get();
        // }
        // return User.builder()
        // .username(member.getEmail())
        // .password(member.getPassword())
        // .role(member.getRole().toString()).build();

        Optional<Member> result = memberRepository.findByEmail(username);

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email");
        }
        Member member = result.get();
        // entity => dto

        return new AuthMemberDto(entityToDto(member));

    }

    //

    @Override
    public String register(MemberDto insertDto) throws IllegalStateException {

        // 중복 이메일 확인
        validateDuplicateEmail(insertDto.getEmail()); //
        // 비밀번호 암호화
        insertDto.setPassword(passwordEncoder.encode(insertDto.getPassword()));
        // 권한 부여
        insertDto.setRole(MemberRole.MEMBER);

        Member member = dtoToEntity(insertDto);
        memberRepository.save(member);
        return member.getEmail();
    }

    // 중복 이메일 검사
    private void validateDuplicateEmail(String email) throws IllegalStateException {
        Optional<Member> result = memberRepository.findByEmail(email);
        if (result.isPresent()) {
            throw new IllegalStateException("중복된 이메일입니다.");
        }
    }

    @Transactional
    @Override
    public void nicknameUpdate(MemberDto updateDto) {
        // memberRepository.save(dtoToEntity(updateDto));

        memberRepository.updateNickname(updateDto.getNickname(),
                updateDto.getEmail());
    }

    @Override
    public void passwordUpdate(PasswordChangeDto pDto) throws IllegalStateException {
        // 현재 이메일과 비밀번호 일치 여부 => true => 비밀번호 변경
        // select => 결과 있다면 => update
        Member member = memberRepository.findByEmail(pDto.getEmail()).get();
        // 비밀번호는 암호화된 상태
        // passwordEncoder.encode(pDto.getCurrentPassword());
        // passwordEncoder.matches(rawP, encodeP) 1111, {bcypt}~~
        if (!passwordEncoder.matches(pDto.getCurrentPassword(), member.getPassword())) {
            throw new IllegalStateException("현재 비밀번호가 일치하지않음");
        } else {
            member.setPassword(passwordEncoder.encode(pDto.getNewPassword()));
            memberRepository.save(member);
        }
    }

    @Transactional
    @Override
    public void leave(MemberDto LeaveMemberDto) throws IllegalStateException {
        // 이메일과 비밀번호 일치 시
        Member member = memberRepository.findByEmail(LeaveMemberDto.getEmail()).get();
        if (!passwordEncoder.matches(LeaveMemberDto.getPassword(), member.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지않음");
        } else {
            reviewRepository.deleteByMember(member);
            memberRepository.delete(member);
        }
    }

}
