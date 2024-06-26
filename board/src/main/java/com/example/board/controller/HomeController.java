package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.PageRequestDto;

@Controller
public class HomeController {

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public String getHome(RedirectAttributes rttr, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        // PageRequestDto requestDto : new PageRequestDto 해서 default 생성자 만들어줌
        // 사실 나는 dto에 기본값 줘서 이거 안넣어도 잘 가긴함
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/board/list";
    }

    // 무슨 내용이 오는지 확인용
    @PreAuthorize("permitAll()")
    @ResponseBody
    @GetMapping("/auth")
    public Authentication getAuth() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }

}
