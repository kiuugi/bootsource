package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.movie.dto.PageRequestDto;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        return "redirect:/movie/list";
    }

    @GetMapping("/access-denied")
    public void getDenied(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("접근 제한");
    }

    @GetMapping("/error")
    public String getError(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("404");
        return "/except/url404";
    }

    // 무슨 내용이 오는지 확인용
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @GetMapping("/auth")
    public Authentication getAuth() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        // 로그인 성공시 authentication 객체생성(로그인 정보가 담겨있음)
        return authentication;
    }
}
