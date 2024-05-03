package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movie.dto.AuthMemberDto;
import com.example.movie.dto.MemberDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PasswordChangeDto;
import com.example.movie.service.MovieUserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MovieUserService movieUserService;

    // login Post는 작성X => Security에서 해줌 -> DB 확인 작업을 할 수 있도록 service는 만들기
    // UserDetailsService 상속 필요
    @GetMapping("/login")
    public void getLogin(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("로그인 폼 요청 login controller");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public void getProfile(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("프로필 화면 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public String getProfileEdit(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("프로필 수정화면 요청");
        return "/member/edit-profile";
    }

    // /edit/nickname
    @PostMapping("/edit/nickname")
    public String postUpdateNickname(MemberDto memberDto, HttpSession session) {
        log.info("edit nickname {}", memberDto);
        movieUserService.nicknameUpdate(memberDto);
        // SecurityContent 안에 저장된 Authentication이 변경되지는 않음
        // 1) 현재 세션 제거 => 재로그인
        // session.invalidate(); / 새션 날리는법

        // 2) Authentication 업데이트
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();
        authMemberDto.getMemberDto().setNickname(memberDto.getNickname()); // 전 닉네임 자리에 입력받은 닉네임을 넣어줌

        SecurityContextHolder.getContext().setAuthentication(authentication); // authentication 리로드

        return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/password")
    public String postUpdatePassword(PasswordChangeDto pDto, HttpSession session, RedirectAttributes rttr) {
        try {
            movieUserService.passwordUpdate(pDto);
        } catch (Exception e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/edit";
        }

        session.invalidate();
        return "redirect:/member/profile";
    }

    @GetMapping("/register")
    public void getRegister(MemberDto memberDto, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("register 요청");
    }

    @PostMapping("/register")
    public String postRegister(@Valid MemberDto memberDto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("post register 요청");

        if (result.hasErrors()) {
            return "/member/register";
        }
        String email = "";
        try {
            email = movieUserService.register(memberDto);

        } catch (Exception e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/register";
        }

        rttr.addFlashAttribute("email", email);
        return "redirect:/member/login";
    }

    @GetMapping("/leave")
    public void getLeave(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("leave controller 회원탈퇴");
    }

    @PostMapping("/leave")
    public String postLeave(MemberDto memberDto, HttpSession session, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("leave post controller 회원 탈퇴 폼 요청 {}", memberDto);
        try {
            movieUserService.leave(memberDto);
        } catch (Exception e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/leave";
        }
        session.invalidate();
        return "redirect:/movie/list";
    }

}
