package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/book")
public class BookController {

    private final BookService service;

    @GetMapping("/list")
    public void getBookList(Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("list 요청");
        PageResultDto<BookDto, Book> result = service.getList(requestDto);
        model.addAttribute("result", result);
    }

    @GetMapping("/create")
    public void getBookCreate(BookDto dto, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("create 요청");
        model.addAttribute("cateName", service.categoryNameList());
    }

    @PostMapping("/create")
    public String postCreate(@Valid BookDto bookDto, BindingResult result, RedirectAttributes rttr, Model model,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("book post 요청 {}", bookDto);
        // 유효성 체크
        if (result.hasErrors()) {
            model.addAttribute("cateName", service.categoryNameList());
            return "/book/create";
        }
        // insert
        Long id = service.bookCreate(bookDto);
        rttr.addFlashAttribute("msg", id);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/book/list";
    }

    // ?id=345&page=5&type=&keyword= 페이지 나누기 후 경로
    @GetMapping({ "/read", "/modify" })
    public void getBookRead(@RequestParam Long id, Model model,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("read, modify 요청");
        BookDto dto = service.getRow(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postMethodName(BookDto updateDto, @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {
        log.info("업데이트 요청 {}", updateDto);
        log.info("page 나누기 정보 {}", requestDto);

        Long id = service.update(updateDto);

        // 조회 화면으로 이동
        rttr.addAttribute("id", id);
        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/book/read";
    }

    @PostMapping("/delete")
    public String getMethodName(@RequestParam Long id, @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {
        log.info("delete 요청 {}", id);

        service.bookDelete(id);

        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/book/list";
    }

}
