package com.example.guestbook.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
public class HomeController {
    @GetMapping("/")
    public String getHome(RedirectAttributes rttr) {
        log.info("Home 요청");
        return "redirect:/guestbook/list";
    }
}
