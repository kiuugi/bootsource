package com.example.web1.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
public class HomeController {
    // String, void 형태의 메소드 작성
    // @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/")
    public String home() {
        // c.e.web1.controller.HomeController : home 요청
        log.info("home 요청"); // sout
        // 2024-04-01T12:02:51.272+09:00 INFO 8416 --- [web1] [nio-8080-exec-1]
        // c.e.web1.controller.HomeController : home 요청

        // templates 아래 경로부터 시작 확장자 빼고
        return "index";
    }

    // RedirectAttributes : redirect 시 데이터 전달
    @GetMapping("/ex3") // 받는 주소라서 없는 주소라도 가능
    public String ex3(RedirectAttributes rttr) {
        log.info("/ex3 요청");
        // response.sendRedirect(/qList.do)
        // path +="?bno="+bno;
        // rttr.addAttribute("이름", 보내고싶은 데이터) 파라메터로 전달
        // rttr.addFlashAttribute("이름", 값) Sesstion을 이용해서 값을 저장 sesstion에 임시로 저장
        // rttr.addAttribute("bno", 15); // => /sample/basic?bno=15
        rttr.addFlashAttribute("bno", 20); // => ;jsessionid=B485FA67A7E89BFD5047A43C670CAD28
        // return "redirect:/"
        return "redirect:/sample/basic"; // 경로지정(다른 컨트롤러에 있는 경로 포함해서)
    }

    // IllegalStateException: Ambiguous mapping : 같은 맵핑방식, 같은 주소 => 에러
    // @GetMapping("/ex3")
    // public void ex4() {
    // log.info("/ex3 요청");
    // }

}
