package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
@RequestMapping("/movie")
public class MovieController {

    @GetMapping("/list")
    public void getList() {
        log.info("list 요청 movie_controller");
    }

}
