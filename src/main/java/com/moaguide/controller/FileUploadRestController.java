package com.moaguide.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@RestController
@RequestMapping("/file")
public class FileUploadRestController {

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            // 1. 파일 이름 생성
            StringBuilder sb = new StringBuilder();
            Random rd = new Random();
            for (int i = 0; i < 10; i++) {
                sb.append((char) (rd.nextInt(26) + 65)); // 랜덤 대문자 생성
            }
            String fileName = sb.toString() + ".pdf";

            // 2. 파일 저장 경로 설정 (static/pdf)
            String uploadDir = "/app/resources/static/pdf/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉토리 없으면 생성
            }

            Path filePath = Paths.get(uploadDir + fileName);

            // 3. 파일 저장
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 4. 응답 반환
            return ResponseEntity.ok().body(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus  .INTERNAL_SERVER_ERROR)
                    .body("File upload failed.");
        }
    }

}
