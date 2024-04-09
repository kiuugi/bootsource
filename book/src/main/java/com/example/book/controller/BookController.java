package com.example.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/book")
public class BookController {
    @GetMapping("/list")
    public String getBookList() {
        return "/book/list";
    }

    @GetMapping("/create")
    public String getBookCreate() {
        return "/book/create";
    }

    @GetMapping("/read")
    public String getBookRead() {
        return "/book/read";
    }

    @GetMapping("/modify")
    public String getModify() {
        return "/book/modify";
    }

}
