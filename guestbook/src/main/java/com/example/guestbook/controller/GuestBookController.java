package com.example.guestbook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.service.GuestBookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/guestbook")
public class GuestBookController {

    private final GuestBookService service;

    @GetMapping("/list")
    public void getList(Model model) {
        log.info("list 요청");
        // List<GuestBookDto> list = service.getList();
        model.addAttribute("list", service.getList());
    }

}
