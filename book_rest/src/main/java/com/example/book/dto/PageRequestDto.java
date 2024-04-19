package com.example.book.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PageRequestDto {
    private int page;
    private int size;

    // 검색
    private String type;
    private String keyword;

    public PageRequestDto() {
        // defalt 생성자
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}
