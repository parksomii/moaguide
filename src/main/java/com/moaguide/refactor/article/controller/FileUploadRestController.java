package com.moaguide.refactor.article.controller;


import com.moaguide.refactor.article.service.FileService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileUploadRestController {

	private final FileService fileService;

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
			fileService.save(Id, file.getOriginalFilename());

			// 3. 파일 저장 경로 설정 (static/pdf)
			String uploadDir = "/app/resources/static/pdf/";
			File directory = new File(uploadDir);
			if (!directory.exists()) {
				directory.mkdirs(); // 디렉토리 없으면 생성
			}

			Path filePath = Paths.get(uploadDir + file.getOriginalFilename());

			// 4. 파일 저장
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			// 5. 응답 반환
			return ResponseEntity.ok().body(Id);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("File upload failed.");
		} catch (DuplicateKeyException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("키 중복 에러.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("기타 에러");
		}
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> download(@PathVariable("id") String id) {
		try {
			String fileName = fileService.getFileName(id);
			Path filePath = Path.of("/app/resources/static/pdf/", fileName);

			// 파일 내용 읽기
			byte[] fileContent = Files.readAllBytes(filePath);

			// HTTP 응답 반환
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + URLEncoder.encode(fileName, StandardCharsets.UTF_8)
						+ "\"")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileContent.length))
				.body(fileContent);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(null);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}


}
