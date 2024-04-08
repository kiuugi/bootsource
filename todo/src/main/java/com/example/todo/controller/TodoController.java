package com.example.todo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.todo.service.TodoServiceImpl;

@Controller
@Log4j2
public class TodoController {
    TodoServiceImpl serviceImpl;

    // / 로 접속 시 list.html 보여주기
    @GetMapping(value = { "/", "/todo/list" })
    public String getList() {
        log.info("/, /todo/list 요청");
        // serviceImpl.getList();
        return "/todo/list";
    }

    @GetMapping("/todo/read")
    public String getRead() {
        return "/todo/read";
    }

    @GetMapping("/todo/create")
    public String getCreate() {
        return "/todo/create";
    }

    @GetMapping("/todo/done")
    public String getDone() {
        return "/todo/done";
    }

}
