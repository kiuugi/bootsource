package com.example.guestbook.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GuestBookDto {

    private Long gno;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "작가을 입력해주세요")
    private String writer;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
