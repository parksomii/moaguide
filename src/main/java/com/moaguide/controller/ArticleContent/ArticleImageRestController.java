package com.moaguide.controller.ArticleContent;

import com.moaguide.service.ArticleContent.ArticleImageService;
import com.moaguide.service.FileService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/articles/image")
public class ArticleImageRestController {

	private final ArticleImageService articleImageService;

	@PostMapping()
	public ResponseEntity<?> getroadmap(@RequestParam String src) {
		String s3Url = null;
		try {
			byte[] imageBytes = articleImageService.downloadImageFromUrl(src);

			s3Url = articleImageService.uploadImageToS3(imageBytes);

		} catch (Exception e) {
			throw new RuntimeException("S3 업로드 실패: " + e.getMessage(), e);
		}
		return ResponseEntity.ok().body(s3Url.substring(4));
	}

}
