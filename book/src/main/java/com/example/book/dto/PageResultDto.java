package com.example.book.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;
// java Generics
// PageResultDto<DTO, EN> : ~DTO, Entity 객체를 담기 위한 구조 설계
// Box<T> => Box 안에 담을 객체 T 를 설계

@Data
public class PageResultDto<DTO, EN> {

    // 화면에 보여줄 목록 (여기서는 BookDto가 들어감)
    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 현제 페이지 번호
    private int page;

    // 목록 크기(한 페이지에 보여줄 게시물 수)
    private int size;

    private int start, end;
    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;

    // Page<EN> result = bookRepository.findAll(bookRepository.makePredicate(),
    // pageable); 이게 넘어옴
    public PageResultDto(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        // 총 개수 / 한 페이지당 보여줄 게시물 수 (380 / 10)
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        // pageable.getPageNumber() : 사용자가 요청한 페이지 번호(request 에서-1 을 했음 (페이지 번호가 0부터 시작)
        this.page = pageable.getPageNumber() + 1;
        // 한 페이지당 보여줄 게시물 수
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        this.start = tempEnd - 9;
        this.end = totalPage > tempEnd ? tempEnd : totalPage;
        this.prev = start > 1;
        this.next = totalPage > tempEnd;
        // int 타입으로 1~10 todtjd ==> List<Integer> list
        // boxed() : int ==> Integer // boxed()는 객체형태로 바꿔줌
        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
