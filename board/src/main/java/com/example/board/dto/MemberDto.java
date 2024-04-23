package com.example.board.dto;

import com.example.board.constant.MemberRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {

    @NotBlank(message = "email을 확인해주세요")
    private String email;

    @NotBlank(message = "name을 확인해주세요")
    private String name;

    @NotBlank(message = "password를 확인해주세요")
    private String password;

    private MemberRole memberRole;
}
