package com.example.board.dto;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardDto {
    private Long bno;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    @NotBlank(message = "email을 입력해주세요")
    private String writerEmail; // 작성자 @Id
    private String writerName; // 작성자 이름
    private Long replyCount; // 해당 게시물의 댓글 수

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
