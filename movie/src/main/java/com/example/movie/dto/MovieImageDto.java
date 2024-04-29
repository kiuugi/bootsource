package com.example.movie.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
public class MovieImageDto {

    private Long inum;

    private String uuid;

    private String imgname;

    private String path;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    // 파일 저장
    public String getImageURL() {
        String fullPath = "";

        try {
            fullPath = URLEncoder.encode(path + "/" + uuid + "_" + imgname, "UTF-8"); // exception날림
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return fullPath;
    }

    public String getThumbImageURL() {
        String thumbFullPath = "";

        try {
            thumbFullPath = URLEncoder.encode(path + "/s_" + uuid + "_" + imgname, "UTF-8"); // exception날림
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return thumbFullPath;
    }
}
