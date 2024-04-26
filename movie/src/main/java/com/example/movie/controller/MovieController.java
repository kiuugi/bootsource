package com.example.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto pageRequestDto, Model model) {
        log.info("list 요청 movie_controller");
        PageResultDto<MovieDto, Object[]> result = service.getList(pageRequestDto);
        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "modify" })
    public void getRead(@RequestParam Long mno, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("read 요청 controller {}", mno);
        model.addAttribute("dto", service.getRow(mno));
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long mno) {
        log.info("remove 요청 {}", mno);
        service.movieRemove(mno);
        return "redirect:/movie/list";
    }

}
