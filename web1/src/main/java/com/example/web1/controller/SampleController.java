package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/sample") // controller 클래스에서 RequestMapping을 붙여 공통된 경로를 생략가능
public class SampleController {
    // String, void 형태의 메소드 작성
    // 404 : 경로 없음(컨트롤러에 맵핑 주소 확인)
    // 500 : 템플릿이 없는 경우 Error resolving template [sample/basic], template might not
    // exist or might not be accessible by any of the configured Template Resolvers
    // templates 폴더 확인

    // 리턴타입 결정
    // void : 경로와 일치하는 곳에 템플릿이 존재할 때
    // 경로의 마지막 이름(basic)이 html 파일로 설정 / 앞에는 전부 경로(폴더)
    // String : 경로와 일치하는 곳에 템플릿이 없을 때(템플릿의 위치와 관계없이 자유롭게 지정 가능)

    // http://localhost:8080/sample/basic 요청
    @GetMapping("/basic")
    public void basic() {
        log.info("/sample/basic 요청");
        // 2024-04-01T12:20:01.490+09:00 INFO 8416 --- [web1] [nio-8080-exec-4]
        // c.e.web1.controller.SampleController : /sample/basic 요청
    }

    // http://localhost:8080/sample/ex1 요청
    @GetMapping("/ex1")
    public void ex1() {
        log.info("/sample/ex1 요청"); // : /sample/ex1 요청
    }

    @GetMapping("/ex2")
    public String ex2() {
        // http://localhost:8080/sample/ex2 / 나오는 화면이 Board CREATE화면이 나옴
        log.info("/sample/ex2 요청"); // : /sample/ex2 요청
        return "/board/create";
    }

}
