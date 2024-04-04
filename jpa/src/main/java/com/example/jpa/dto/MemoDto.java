package com.example.jpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemoDto {
    private Long mno;
    @NotBlank(message = "메모 내용을 확인해 주세요")
    private String memoText;

}
