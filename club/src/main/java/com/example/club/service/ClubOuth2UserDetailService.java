package com.example.club.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.club.constant.ClubMemberRole;
import com.example.club.dto.ClubAuthMemberDto;
import com.example.club.entity.ClubMember;
import com.example.club.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubOuth2UserDetailService extends DefaultOAuth2UserService {

    private final ClubMemberRepository clubMemberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest {}", userRequest);
        // org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest@6812690c
        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName {}", clientName); // clientName Google
        log.info(userRequest.getAdditionalParameters());
        log.info("====================================");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info("{} : {}", k, v);
            // email : jeung9963@gmail.com
            // email_verified : true
            // picture :
            // https://lh3.googleusercontent.com/a-/ALV-UjVIkt8pkdfJ-tpqqJe8ty8zElei586WJKDViorBgw6YFdLhMw=s96-c
            // sub : 107350745844378937851
        });

        ClubMember clubMember = saveSocialMember(oAuth2User.getAttribute("email"));
        // entity => dto
        ClubAuthMemberDto clubAuthMemberDto = new ClubAuthMemberDto(clubMember.getEmail(), clubMember.getPassword(),
                clubMember.isFromSocial(), clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()),
                oAuth2User.getAttributes());
        clubAuthMemberDto.setName(clubMember.getName());

        return clubAuthMemberDto;

    }

    private ClubMember saveSocialMember(String email) {
        // 이미 가입되어 있는지 확인
        Optional<ClubMember> result = clubMemberRepository.findByEmailAndFromSocial(email, true);

        if (result.isPresent()) {
            return result.get();
            // 가입되어있다면 리턴
        }

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1234")) // 애초에 소셜 로그인으로 들어와서 비밀번호는 의미없음
                .fromSocial(true)
                .build();
        clubMember.addMemberRole(ClubMemberRole.USER);
        clubMemberRepository.save(clubMember);
        return clubMember;
    }
}
