package com.example.book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;

@SpringBootTest
public class BookSerciveTest {
    @Autowired
    private BookService bookService;

    @Test
    @Transactional
    public void testList() {
        PageRequestDto requestDto = PageRequestDto.builder().page(1).size(10).build();
        PageResultDto<BookDto, Book> pageResultDto = bookService.getList(requestDto);

        System.out.println("----------페이지 나누기 정보--------");
        System.out.println("prev " + pageResultDto.isPrev());
        System.out.println("next " + pageResultDto.isNext());
        System.out.println("total " + pageResultDto.getTotalPage());
        System.out.println("pageList " + pageResultDto.getPageList());

        pageResultDto.getDtoList().forEach(System.out::println);
    }

    @Test
    @Transactional
    public void testSearchList() {
        PageRequestDto requestDto = PageRequestDto.builder().page(1).size(10).type("t").keyword("미술").build();
        PageResultDto<BookDto, Book> pageResultDto = bookService.getList(requestDto);

        pageResultDto.getDtoList().forEach(System.out::println);
    }
}
