package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDto;
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
    public void getBookList(Model model) {
        log.info("list 요청");
        List<BookDto> list = service.getList();
        model.addAttribute("list", list);
    }

    @GetMapping("/create")
    public void getBookCreate(BookDto dto, Model model) {
        log.info("create 요청");
        model.addAttribute("cateName", service.categoryNameList());
    }

    @PostMapping("/create")
    public String postCreate(@Valid BookDto bookDto, BindingResult result, RedirectAttributes rttr, Model model) {
        log.info("book post 요청 {}", bookDto);
        // 유효성 체크
        if (result.hasErrors()) {
            model.addAttribute("cateName", service.categoryNameList());
            return "/book/create";
        }
        // insert
        Long id = service.bookCreate(bookDto);
        rttr.addFlashAttribute("result", id);
        return "redirect:/book/list";
    }

    @GetMapping({ "/read", "/modify" })
    public void getBookRead(@RequestParam Long id, Model model) {
        log.info("read, modify 요청");
        BookDto dto = service.getRow(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postMethodName(BookDto updateDto, RedirectAttributes rttr) {
        log.info("업데이트 요청 {}", updateDto);

        Long id = service.update(updateDto);
        rttr.addAttribute("id", id);
        return "redirect:/book/read";
    }

    @PostMapping("/delete")
    public String getMethodName(@RequestParam Long id) {
        log.info("delete 요청 {}", id);

        service.bookDelete(id);

        return "redirect:/book/list";
    }

}
