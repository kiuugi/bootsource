package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.LoginDto;
import com.example.web1.dto.MemberDto;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/login")
    public void login(LoginDto loginDto) { // form 값을 받을때 객체를 지정하기 위해 LoginDto loginDto 를 써줌
        log.info("로그인 페이지 요청");
    }

    // @PostMapping("/login")
    // public void loginPost(String email, String name) {
    // log.info("email {}", email); // {} 없으면 사용자가 담은 값이 안나옴 뭔지 모르겠음
    // log.info("name {}", name);
    // }

    // @PostMapping("/login")
    // public String loginPost(@ModelAttribute("mDto") LoginDto loginDto,
    // @ModelAttribute("page") int page, Model model) {
    // log.info("email {}", loginDto.getEmail()); // {} 없으면 사용자가 담은 값이 안나옴 뭔지 모르겠음
    // log.info("name {}", loginDto.getName());
    // log.info("page {}", page);

    // // model.addAttribute("page", page); == @ModelAttribute("page") int page
    // return "/member/info";
    // // fowrad 방식
    // }
    // @Valid LoginDto : LoginDto 의 유효성 검사 / 검사 값이 BindingResult로 들어감
    @PostMapping("/login")
    public String loginPost(@Valid LoginDto loginDto, BindingResult result) {
        log.info("로그인 정보 가져오기");
        log.info("email {}", loginDto.getEmail()); // {} 없으면 사용자가 담은 값이 안나옴 뭔지 모르겠음
        log.info("name {}", loginDto.getName());

        // 유효성 검증을 통과하지 못한다면
        if (result.hasErrors()) {
            return "/member/login";
        }
        return "/member/info";

    }

    // 데이터 보내기
    // request.setAttribute("이름", 값) => ${} == model.addAttribute("page", page);

    @GetMapping("/join")
    public void join(MemberDto memberDto) { // validate 하기위해 MemberDto가 필요
        log.info("join 요청");

    }

    @PostMapping("/join")
    public String joinPost(@Valid MemberDto memberDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/member/join";
        }
        return "redirect:/member/login";
    }

}
