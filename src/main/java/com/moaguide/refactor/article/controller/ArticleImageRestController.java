package com.moaguide.refactor.article.controller;

import com.moaguide.service.ArticleContent.ArticleImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
		return ResponseEntity.ok().body("https://d2qf2amuam62ps.cloudfront.net/"+s3Url);
	}

}
