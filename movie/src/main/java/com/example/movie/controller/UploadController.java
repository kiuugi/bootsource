package com.example.movie.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
@RequestMapping("/upload")
public class UploadController {

    // application.properties 설정한 변수 가져오기
    @Value("${com.example.upload.path}")
    private String uploadPath;

    @GetMapping("/ex1")
    public void getUpload() {
        log.info("upload Foam 요청 ");
    }

    // fetch ==> @RestController

    @PostMapping("/uploadAjax")
    public void postMethodName(MultipartFile[] uploadFiles) {

        for (MultipartFile multipartFile : uploadFiles) {
            String oriName = multipartFile.getOriginalFilename();
            String fileName = oriName.substring(oriName.lastIndexOf("\\") + 1);
            log.info("파일정보 - 전체경로 : {} ", oriName); // 파일정보 - 전체경로 : boy1.jpg / 전체경로 : boy2.jpg
            log.info("파일 정보 - 파일명 : {} ", fileName); // 파일 정보 - 파일명 : boy1.jpg / 파일 정보 - 파일명 : boy2.jpg
            // 폴더 생성
            String saveFolderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + saveFolderPath + File.separator + uuid + "_" + fileName;
            // java.nio.Path
            Path savePath = Paths.get(saveName);
            try {
                multipartFile.transferTo(savePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 일반 controller 라서 템플릿을 찾고있지만 /uploadAjax라는 html은 없어서 에러나고있긴함
    }

    private String makeFolder() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderStr = dateStr.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderStr);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderStr;
    }

}
