package com.example.movie.dto;

import java.time.LocalDateTime;

import com.example.movie.constant.MemberRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class MemberDto {

    private Long mid;
    @Email(message = "email 형식이 아닙니다.")
    @NotBlank(message = "email을 입력해주세요")
    private String email;

    @NotBlank(message = "password 입력해주세요")
    private String password;

    @NotBlank(message = "nickname 입력해주세요")
    private String nickname;

    private MemberRole role;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
