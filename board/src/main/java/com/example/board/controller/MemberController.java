package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.dto.PageRequestDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public void getLogin() {
        // @ModelAttribute("requestDto") PageRequestDto requestDto, Model model 나는 dto
        // 초기값을 줘서 안줘도 에러는 안뜸
        // model.addAttribute(requestDto);
        log.info("로그인 요청");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/register")
    public void getRegister() {
        log.info("/회원가입 요청");
    }

    @PostMapping("/register")
    public String postRegister() {

        return "redirect:/member/login";
    }

}
