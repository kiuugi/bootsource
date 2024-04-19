package com.example.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.book.dto.PageRequestDto;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getHome(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        return "/home";
    }

}
