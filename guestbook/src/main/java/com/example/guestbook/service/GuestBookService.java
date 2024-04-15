package com.example.guestbook.service;

import java.util.List;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.entity.GuestBook;

public interface GuestBookService {

    List<GuestBookDto> getList(); // 아아 페이지를 나누기 전에만 쓰이는 범부여

    public default GuestBookDto entityToDto(GuestBook entity) {
        return GuestBookDto.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .writer(entity.getWriter())
                .content(entity.getContent())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }

    public default GuestBook dtoToEntity(GuestBookDto dto) {
        return GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .content(dto.getContent())
                .build();
    }

}