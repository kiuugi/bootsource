package com.example.web1.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class HomeController {
    // String, void 형태의 메소드 작성
    // @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/")
    public String home() {
        // c.e.web1.controller.HomeController : home 요청
        log.info("home 요청"); // sout
        // 2024-04-01T12:02:51.272+09:00 INFO 8416 --- [web1] [nio-8080-exec-1]
        // c.e.web1.controller.HomeController : home 요청

        // templates 아래 경로부터 시작 확장자 빼고
        return "index";
    }
}
