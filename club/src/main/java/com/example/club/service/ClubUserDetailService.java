package com.example.club.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.club.constant.ClubMemberRole;
import com.example.club.dto.ClubAuthMemberDto;
import com.example.club.dto.ClubMemberDto;
import com.example.club.entity.ClubMember;
import com.example.club.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ClubUserDetailService implements UserDetailsService, ClubMemberService {

    private final ClubMemberRepository clubMemberRepository;

    private final PasswordEncoder passwordEncoder;

    // auth 쓰고 봤던 그 정보들을 담는 객체
    // UserDetails <- User(impl/UserDetails 구현) <- CustomUser(User 상속)
    // 지금은(ClubAuthMemberDto)

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 담당 메소드
        // UserDetails를 return 해야 하는데 UserDetails는 인터페이스이기 때문에 UserDetails는를 구현하고있는 다른
        // 클래스를 리턴함
        log.info("로그인 요청 {}", username);

        Optional<ClubMember> result = clubMemberRepository.findByEmailAndFromSocial(username, false); // 그냥 get 써도됨

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("이메일 혹은 social 확인");
        }

        ClubMember clubMember = result.get();
        log.info("================");
        log.info(clubMember);
        log.info("================");

        // entity ==> dto
        ClubAuthMemberDto clubAuthMemberDto = new ClubAuthMemberDto(clubMember.getEmail(), clubMember.getPassword(),
                clubMember.isFromSocial(), clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()));
        // role 이 여러개있는 user가 있기 때문에 strem을 씀
        // auth 에서는 (ROLE_)가 자동으로 붙지만 여기서 가져올 때는 자동이아니라 따로 써줌
        clubAuthMemberDto.setName(clubMember.getName());

        return clubAuthMemberDto;
    }

    @Override
    public String register(ClubMemberDto memberDto) {
        // dto => entity 변경
        // dto원본 비밀번호 ==> 암호화
        ClubMember clubMember = ClubMember.builder()
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .fromSocial(false)
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .build();
        // role 부여
        clubMember.addMemberRole(ClubMemberRole.USER);

        return clubMemberRepository.save(clubMember).getEmail();
    }

}
