package com.example.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;
import com.example.book.repository.PublisherRepository;

public interface BookService {

    // 페이지 나누기 전
    // List<BookDto> getList();

    // 페이지 나누기
    PageResultDto<BookDto, Book> getList(PageRequestDto requestDto);

    Long bookCreate(BookDto dto);

    List<String> categoryNameList();

    List<String> publisherNameList();

    BookDto getRow(Long id);

    Long update(BookDto updateDto);

    void bookDelete(Long id);

    public default BookDto entityToDto(Book entity) {
        return BookDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .writer(entity.getWriter())
                .price(entity.getPrice())
                .salePrice(entity.getSalePrice())
                .createDate(entity.getCreatedDate())
                .lastModifyedDate(entity.getLastModifiedDate())
                .categoryName(entity.getCategory().getName())
                .publisherName(entity.getPublisher().getName())
                .build();
    }

    public default Book dtoToEntity(BookDto dto) {
        // category 와 publisher 는 이름이 넘어옴

        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .price(dto.getPrice())
                .salePrice(dto.getSalePrice())
                .build();
    }

}
