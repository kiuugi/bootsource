package com.example.board.dto;

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
@Builder
@ToString
public class ReplyDto {
    private Long rno;

    private String text; // 댓글 내용

    private String writerEmail; // 작성자 @Id
    private String writerName; // 작성자 이름

    private Long boardId;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
