package com.example.movie.dto;

import groovyjarjarantlr4.v4.parse.ANTLRParser.prequelConstruct_return;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDto {
    private String email;
    private String currentPassword;
    private String newPassword;

}
