package com.example.movie.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.movie.dto.ReviewDto;
import com.example.movie.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // /3/all
    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDto>> getList(@PathVariable("mno") Long mno) {
        log.info("review RestController mno {}", mno);
        return new ResponseEntity<>(reviewService.getListOfMovie(mno), HttpStatus.OK);
    }

}
