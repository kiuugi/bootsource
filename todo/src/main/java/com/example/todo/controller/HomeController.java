package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {
    // / 로 접속 시 list.html 보여주기
    @GetMapping(value = { "/" })
    public String getList() {
        log.info("home 요청");

        return "redirect:/todo/list";
    }
}
