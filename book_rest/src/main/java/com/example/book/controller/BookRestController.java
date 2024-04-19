package com.example.book.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Log4j2
@RestController
public class BookRestController {

    private final BookService service;

    // /pages/1 -> 10개 데이터 가져오기
    @GetMapping("/pages/{page}")
    public ResponseEntity<PageResultDto<BookDto, Book>> getBookList(@PathVariable("page") int page) {
        log.info("list 요청");
        PageRequestDto requestDto = new PageRequestDto();
        requestDto.setPage(page);
        PageResultDto<BookDto, Book> result = service.getList(requestDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<BookDto> getBookRead(@PathVariable("id") Long id) {
        log.info("read, modify 요청");

        BookDto dto = service.getRow(id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    // @RequestBody : json 으로 넘어오는 데이터를 객체 바인딩
    @PostMapping("/book/new")
    public ResponseEntity<String> postCreate(@RequestBody @Valid BookDto bookDto) {
        // @Valid에서 에러가 나면 BindingResult가 가져감
        log.info("book post 요청 {}", bookDto);

        // insert
        Long id = service.bookCreate(bookDto);

        // Vaild 검증 성공한 경우
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // @Valid 검증에 실패한 경우 ExceptionHandler가 받아서 메소드 실행
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>(); // Key, value 형태의 객체가 Map 밖에 없어서 Map을 불러줌

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // /modify/3 + 데이터
    @PutMapping("/modify/{id}")
    public ResponseEntity<String> postMethodName(@RequestBody BookDto updateDto, @PathVariable("id") Long id) {
        log.info("업데이트 요청 {}", updateDto);

        service.update(updateDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> getMethodName(@PathVariable("id") Long id) {
        log.info("delete 요청 {}", id);

        service.bookDelete(id);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
