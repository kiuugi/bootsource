package com.example.guestbook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.service.GuestBookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/guestbook")
public class GuestBookController {

    private final GuestBookService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("list 요청");

        // model.addAttribute("list", service.getList());
        PageResultDto<GuestBookDto, GuestBook> result = service.getList(requestDto);
        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRow(@RequestParam Long gno, @ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        // @RequestParam는 메서드 매개변수를 HTTP 요청 매개변수와 매핑하는 데 사용됨
        log.info("read 요청");
        model.addAttribute("dto", service.getRow(gno));
    }

    @PostMapping("/modify")
    public String postModify(GuestBookDto guestBookDto, @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {
        log.info("modify post 요청 {}", guestBookDto);
        service.modify(guestBookDto);
        rttr.addAttribute("gno", guestBookDto.getGno());

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/delete")
    public String postDelete(@RequestParam Long gno, @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {
        log.info("delete 요청 {}", gno);
        service.delete(gno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/guestbook/list";
    }

    @GetMapping("/create")
    public void getCreate(GuestBookDto guestBookDto) {
        // 유효성 검사를 위해서 비어있는 DTO라도 보냄
        log.info("create 요청");
    }

    @PostMapping("/create")
    public String postCreate(@Valid GuestBookDto guestBookDto, BindingResult result, RedirectAttributes rttr) {
        log.info("postCreate 요청 {}", guestBookDto);

        if (result.hasErrors()) {
            return "/guestbook/create";
        }

        Long gno = service.create(guestBookDto);
        rttr.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

}
