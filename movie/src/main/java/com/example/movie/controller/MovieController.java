package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/modify")
    public String postModify(MovieDto movieDto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("movie 수정 요청 {}", movieDto);
        Long mno = service.movieUpdate(movieDto);
        rttr.addFlashAttribute("msg", mno);
        rttr.addAttribute("mno", movieDto.getMno());
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/movie/read";
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long mno, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("remove 요청 {}", mno);
        service.movieRemove(mno);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/movie/list";
    }

    @GetMapping("/register")
    public void getRegister(RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("영화 등록 폼 요청 register");
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public String postRegister(MovieDto movieDto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("영화 등록 {}", movieDto);

        // 서비스 호출
        Long mno = service.movieInsert(movieDto);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("size", requestDto.getSize());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        // mno 넘기기
        rttr.addFlashAttribute("msg", mno);
        return "redirect:/movie/list";
    }

}
