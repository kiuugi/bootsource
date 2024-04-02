package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.MemberDto;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/login")
    public void login() {
        log.info("로그인 페이지 요청");
    }

    // @PostMapping("/login")
    // public void loginPost(String email, String name) {
    // log.info("email {}", email); // {} 없으면 사용자가 담은 값이 안나옴 뭔지 모르겠음
    // log.info("name {}", name);
    // }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("mDto") MemberDto loginDto, @ModelAttribute("page") int page, Model model) {
        log.info("email {}", loginDto.getEmail()); // {} 없으면 사용자가 담은 값이 안나옴 뭔지 모르겠음
        log.info("name {}", loginDto.getName());
        log.info("page {}", page);

        // model.addAttribute("page", page); == @ModelAttribute("page") int page
        return "/member/info";
        // fowrad 방식
    }

    // 데이터 보내기
    // request.setAttribute("이름", 값) => ${} == model.addAttribute("page", page);
}
