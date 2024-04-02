package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class BoardController {
    @GetMapping("/board/create")
    public void create() {
        log.info("create 요청"); // : create 요청
    }
}
