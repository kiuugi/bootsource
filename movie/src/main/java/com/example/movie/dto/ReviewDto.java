package com.example.movie.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long reviewNo;

    private int grade;

    private String text;

    // Member 관계
    // 전체가 아니라 일부분만 가져와도 괜찮은 상황이라면 일부분만 가져옴
    private Long mid;
    private String email;
    private String nickname;

    // Movie 관계
    private Long mno;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
