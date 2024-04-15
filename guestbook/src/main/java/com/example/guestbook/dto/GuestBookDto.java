package com.example.guestbook.dto;

import java.time.LocalDateTime;

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

    private String title;

    private String writer;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
