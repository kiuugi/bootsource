package com.example.book.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    private Long id;

    @NotBlank(message = "제목을 입력해주세요") // 검증조건
    private String title;

    @NotBlank(message = "작가를 입력해주세요")
    private String writer;

    @NotNull(message = "가격을 입력해주세요") // Integer 타입이라서 NotNull로함
    private Integer price;

    @NotNull(message = "할인가격을 입력해주세요")
    private Integer salePrice;

    private LocalDateTime createDate;
    private LocalDateTime lastModifyedDate;

    // 관계 변수
    @NotBlank(message = "카테고리를 선택해주세요")
    private String categoryName;
    @NotBlank(message = "출판사를 선택해주세요")
    private String publisherName;
}
