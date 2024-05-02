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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

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

    // /mno + Post
    @PostMapping("/{mno}")
    public ResponseEntity<Long> postAddReview(@PathVariable("mno") Long mno, @RequestBody ReviewDto reviewDto) {
        log.info("add review controller reviewDto {}", reviewDto);
        // Long reviewNo = reviewService.addReview(reviewDto);
        return new ResponseEntity<>(reviewService.addReview(reviewDto), HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewNo}")
    public ResponseEntity<Long> deleteReview(@PathVariable("reviewNo") Long reviewNo) {
        log.info("리뷰 삭제 {}", reviewNo);
        reviewService.removeReview(reviewNo);
        return new ResponseEntity<>(reviewNo, HttpStatus.OK);
    }

    @GetMapping("/{mno}/{reviewNo}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable("reviewNo") Long reviewNo) {
        log.info("리뷰 한개 읽어오기{}", reviewNo);

        return new ResponseEntity<>(reviewService.getReview(reviewNo), HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewNo}")
    public ResponseEntity<Long> putReview(@PathVariable("reviewNo") Long reviewNo, @RequestBody ReviewDto reviewDto) {
        log.info("put controller 리뷰 수정 {}", reviewDto);

        return new ResponseEntity<>(reviewService.updateReview(reviewDto), HttpStatus.OK);
    }

}
