package com.example.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.rest.dto.SampleDto;

@Controller
public class BasicController {

    @GetMapping("/basic")
    @ResponseBody // 리턴값이 데이터임 (응답을 html의 body로 받아라~) RestController와 같아짐
    public String getMethodName() {
        return "반갑습니다";
    }

    // ResponseEntity : 일반 컨트롤러에서도 리턴값이 데이터임을 의미함 (@ResponseBody 필요없음)
    @GetMapping(value = "/check2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SampleDto> getCheck(double height, double weight) {
        SampleDto dto = new SampleDto();
        dto.setFirstName(String.valueOf(height));
        dto.setLastName(String.valueOf(weight));
        dto.setMno(1L);

        if (height < 150) {
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
        } else {
            // 200 : OK
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }

    }
}
