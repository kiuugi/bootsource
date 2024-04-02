package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.AddDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/calc")
public class AddController {
    @GetMapping("/add")
    public void addGet() {
        log.info("/calc/add 요청");

    }

    // 사용자 입력값 가져오기
    // 1. HttpServletRequest
    // 2. 파라메터 이용(폼 이름과 변수명 일치)
    // 3. DTO 이용
    // - post 컨트롤러 응답 후 보여지는 화면 단에서 dto에 들어 있는 값을 사용 가능

    // @PostMapping("/add")
    // public void addPost(HttpServletRequest req) {
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", req.getParameter("num1"));
    // log.info("num2 {}", req.getParameter("num2"));
    // }

    // @PostMapping("/add") // string boots 타입 컨버젼이 자동으로 이루어짐 post로 보내준 값은 String
    // 이지만 자동 형변환으로 int 로 담김
    // public void addPost(@RequestParam(value = "num1", defaultValue = "0") int
    // num1,
    // @RequestParam(value = "op2", defaultValue = "0") int num2) {
    // // num1, num2 도 똑같이 이름과 변수명을 그대로 맞춘것(name="num1")
    // // @RequestParam("name") int num1 ==> num1 = ("name")
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", num1);
    // log.info("num2 {}", num2);
    // }

    @PostMapping("/add")
    public void addPost(AddDto dto, Model model) {
        log.info("/calc/add post 요청");
        log.info("num1 {}", dto.getNum1());
        log.info("num1 {}", dto.getNum2());
        log.info("num1 {}", dto.getNum2());

        // dto.setResult(dto.getNum1() + dto.getNum2());
        model.addAttribute("result", dto.getNum1() + dto.getNum2());

    }

    // 사칙연산
    @GetMapping("/rules")
    public void rules() {
        log.info("rules 요청");
    }

    @PostMapping("/rules")
    public String postRules(AddDto addDto, @ModelAttribute("op") String op, Model model) {

        int result = 0;
        switch (op) {
            case "+":
                result = addDto.getNum1() + addDto.getNum2();
                break;
            case "-":
                result = addDto.getNum1() - addDto.getNum2();
                break;
            case "*":
                result = addDto.getNum1() * addDto.getNum2();
                break;
            case "/":
                result = addDto.getNum1() / addDto.getNum2();
                break;

        }

        // model.addAttribute("result", result);
        addDto.setResult(result);

        return "/calc/result";
    }

}
