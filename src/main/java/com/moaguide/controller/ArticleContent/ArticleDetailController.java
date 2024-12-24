package com.moaguide.controller.ArticleContent;

import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.ArticleContent.ArticleDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        // 서비스 호출
        Object articleDetail = articleDetailService.getArticleDetail(articleId, role);
        return ResponseEntity.ok(articleDetail);
    }
}
