package com.example.board.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.ReplyDto;
import com.example.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@Log4j2
@RequestMapping("/replies")
@RestController // 페이지를 넘기는게 아니라 데이터만 넘김
public class ReplyController {

    private final ReplyService service;

    // http://localhost:8080/replies/board/100
    @GetMapping("/board/{bno}")
    public List<ReplyDto> getListByBoard(@PathVariable("bno") Long bno) {
        log.info("댓글 list 가져오기 {}", bno);
        List<ReplyDto> replies = service.getReplies(bno);
        return replies;
    }

    @PostMapping("/new")
    public ResponseEntity<Long> postNew(@RequestBody ReplyDto dto) {
        log.info("댓글 등록 {}", dto);
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> deleteReply(@PathVariable("rno") Long rno) {
        log.info("delete 요청 {}", rno);
        service.remove(rno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/{rno}")
    public ResponseEntity<ReplyDto> getRow(@PathVariable("rno") Long rno) {
        log.info("댓글 하나 요청 {}", rno);
        return new ResponseEntity<ReplyDto>(service.getReply(rno), HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<Long> putModifyReply(@PathVariable("rno") Long rno, @RequestBody ReplyDto replyDto) {
        log.info("reply 수정 요청 {}, {}", rno, replyDto);

        return new ResponseEntity<>(service.update(replyDto), HttpStatus.OK);
    }
}
