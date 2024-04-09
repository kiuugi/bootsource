package com.example.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoService;
import com.example.todo.service.TodoServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Log4j2
@RequiredArgsConstructor // new 안하고 쓸수있게해줌 의존성 주입(DI)
@RequestMapping("/todo")
public class TodoController {

    private final TodoService service;

    // 멤버변수 초기화 1) 생성자 2) setter

    // / 로 접속 시 list.html 보여주기
    @GetMapping(value = { "/list" })
    public String getList(Model model) {
        log.info("/todo/list 요청");
        List<TodoDto> list = service.getList();
        model.addAttribute("list", list); // == setAttribute
        return "/todo/list";
    }

    @GetMapping("/read")
    public void getRead(@RequestParam("id") Long id, Model model) {
        log.info("read 요청");
        TodoDto dto = service.getTodo(id);
        model.addAttribute("dto", dto);
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("create 페이지 요청");
    }

    @PostMapping("/create")
    public String getInsert(TodoDto dto, RedirectAttributes rttr) {
        TodoDto result = service.create(dto);

        rttr.addFlashAttribute("msg", result.getTitle());
        return "redirect:/todo/list";
    }

    @GetMapping("/done")
    public void getDone(Model model) {
        log.info(" /done 완료목록 요청 ");
        List<TodoDto> list = service.getDoneList();
        model.addAttribute("list", list);
    }

    @PostMapping("/update")
    public String postUptare(@RequestParam Long id, RedirectAttributes rttr) {
        log.info("updateTodo 요청하기");
        // id 값 받기
        Long id2 = service.updateTodo(id);
        rttr.addAttribute("id", id2);
        return "redirect:/todo/read";
    }

    @PostMapping("/delete")
    public String postDelete(@RequestParam Long id) {
        log.info("deleteTodo 요청하기");
        service.deleteTodo(id);

        return "redirect:/todo/list";
    }

}
