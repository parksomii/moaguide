package com.moaguide.controller.ArticleContent;

import com.moaguide.dto.RelatedContentDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.ArticleContent.ArticleDetailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles/detail")
@RequiredArgsConstructor
public class ArticleDetailController {

  private final ArticleDetailService articleDetailService;
  private final JWTUtil jwtUtil;

  @GetMapping("/{articleId}")
  public ResponseEntity<Object> getArticleDetail(
      @RequestHeader(value = "Authorization", required = false) String jwt,
      @PathVariable Long articleId) {

    // JWT 유효성 검사
    if (jwt == null || !jwt.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("액세스 토큰이 없습니다.");
    }

    String token = jwt.substring(7); // "Bearer " 제거
    if (jwtUtil.isExpired(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("액세스 토큰이 만료되었습니다.");
    }

    // Role 확인
    String role = jwtUtil.getRole(token);

    // 조회수 증가
    articleDetailService.incrementViews(articleId);

    // 서비스 호출
    Object articleDetail = articleDetailService.getArticleDetail(articleId, role);
    return ResponseEntity.ok(articleDetail);
  }

  @GetMapping("/{articleId}/related")
  public ResponseEntity<Object> getRelatedArticles(
      @RequestHeader(value = "Authorization", required = false) String jwt,
      @PathVariable Long articleId) {

    // JWT 유효성 검사
    if (jwt == null || !jwt.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("액세스 토큰이 없습니다.");
    }

    String token = jwt.substring(7); // "Bearer " 제거
    if (jwtUtil.isExpired(token)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("액세스 토큰이 만료되었습니다.");
    }

    // 관련 콘텐츠 서비스 호출
    List<RelatedContentDto> relatedArticles = articleDetailService.getRelatedArticles(articleId);

    // 관련 콘텐츠가 없는 경우
    if (relatedArticles.isEmpty()) {
      return ResponseEntity.ok("이전 글이 없습니다.");
    }
    return ResponseEntity.ok(relatedArticles);
  }
}