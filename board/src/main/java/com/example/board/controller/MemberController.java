package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.MemberDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.service.MemberService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;

    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public void getLogin(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        // 초기값을 줘서 안줘도 에러는 안뜸
        model.addAttribute(requestDto);
        log.info("로그인 요청");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/register")
    public void getRegister(@ModelAttribute("requestDto") PageRequestDto requestDto, MemberDto memberDto) {
        log.info("/회원가입 요청");

    }

    @PostMapping("/register")
    public String postRegister(@Valid MemberDto memberDto, BindingResult result,
            @ModelAttribute("requestDto") PageRequestDto requestDto, RedirectAttributes rttr) {
        log.info("회원가입 요청 {}", memberDto);
        if (result.hasErrors()) {
            return "/member/register";
        }

        try {
            service.register(memberDto);
        } catch (Exception e) {
            log.info("에러 {}", e.getMessage());
            // e.printStackTrace();
            rttr.addFlashAttribute("dupEmail", e.getMessage()); // session에 잠시 담김
            // rttr.addAttribute("dupEmail", e.getMessage()); 주소줄에 딸려 보냄
            return "redirect:/member/register";
        }

        return "redirect:/member/login";
    }

}
