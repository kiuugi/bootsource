package com.example.jpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.service.MemoServiceImpl;

import jakarta.validation.Valid;

//import com.example.jpa.service.MemoServiceImpl2;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
@Log4j2
@RequestMapping("/memo")
public class MemoController {

    private final MemoServiceImpl service;

    @GetMapping("/list")
    public void getMemoList(Model model) {
        log.info("list 요청");
        List<MemoDto> list = service.getMemoList();

        model.addAttribute("list", list);
    }

    // /memo/read?mno=3 + get
    // /memo/modify?mno=3 + get : 수정을 위해 화면 보여주기 요청
    @GetMapping({ "/read", "/modify" }) // 여러 주소를 받을 수 있음{}
    public void getRead(@RequestParam Long mno, Model model) {
        log.info("read form 요청");

        MemoDto mDto = service.getMemo(mno);
        model.addAttribute("mDto", mDto);

        // 템플릿 찾기
        // /memo/read => templates 폴더 아래 /memo/read.html
        // /memo/read => templates 폴더 아래 /memo/modify.html
    }

    // /memo/modify + post : 실제 수정 요청
    @PostMapping({ "/modify" })
    public String modifyPost(MemoDto mDto, RedirectAttributes rttr) {
        log.info("modify post 요청" + mDto);
        MemoDto updateDto = service.updateMemo(mDto);

        // 수정 완료 시 read로
        rttr.addAttribute("mno", updateDto.getMno());
        return "redirect:/memo/read";
    }

    // /memo/delete?mno=3 + get : 삭제요청
    @GetMapping("/delete")
    public String deleteGet(@RequestParam Long mno) {
        log.info("메모 삭제 요청 {}", mno);
        service.deleteMemo(mno);
        // 삭제 성공 시 리스트
        return "redirect:/memo/list";
    }

    // /memo/create + get : 입력을 위해 화면 보여주기 요청
    @GetMapping("/create")
    public void createGet(@ModelAttribute("mDto") MemoDto mDto) { // MemoDto를 ModelAttribute("mDto") mDto로 부를 수 있도록 바꿔줌
                                                                  // // Valid 쓰기위해서 하나 비어있는 Dto를 보냄
        log.info("메모 입력 폼 요청");

    }

    // /memo/create = post : 실제 입력 요청
    @PostMapping("/create")
    public String postMethodName(@Valid @ModelAttribute("mDto") MemoDto mDto, BindingResult result,
            RedirectAttributes rttr) { // Valid 유효성 검사
        log.info("create post 요청 {}", mDto);

        if (result.hasErrors()) {
            return "/memo/create";
        }

        service.insertMemo(mDto);
        // 그냥 addAttribute == 주소줄로 따라감
        // addFlashAttribute ==
        rttr.addFlashAttribute("result", "SUCCESS");
        return "redirect:/memo/list";
    }

}
