package com.moaguide.controller;


import com.moaguide.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileUploadRestController {

    private final FileService fileService;
    private final ResourceLoader resourceLoader;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            // 1. 파일 이름 생성
            StringBuilder sb = new StringBuilder();
            Random rd = new Random();
            for (int i = 0; i < 10; i++) {
                sb.append((char) (rd.nextInt(26) + 65)); // 랜덤 대문자 생성
            }
            String Id = sb.toString();

            // 2.id 저장
            fileService.save(Id,file.getName());

            // 3. 파일 저장 경로 설정 (static/pdf)
            String uploadDir = "/app/resources/static/pdf/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉토리 없으면 생성
            }

            Path filePath = Paths.get(uploadDir + file.getName());

            // 4. 파일 저장
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 5. 응답 반환
            return ResponseEntity.ok().body(Id);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus  .INTERNAL_SERVER_ERROR)
                    .body("File upload failed.");
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus  .INTERNAL_SERVER_ERROR)
                    .body("키 중복 에러.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus  .INTERNAL_SERVER_ERROR)
                    .body("기타 에러");
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") String id) {
        try {
            String fileName = fileService.getFileName(id);
            Resource resource = resourceLoader.getResource("/app/resources/static/pdf/"+fileName);
            File file = resource.getFile();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,file.getName())	//다운 받아지는 파일 명 설정
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))	//파일 사이즈 설정
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())	//바이너리 데이터로 받아오기 설정
                    .body(resource);	//파일 넘기기
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
