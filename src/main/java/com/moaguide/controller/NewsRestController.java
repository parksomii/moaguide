package com.moaguide.controller;


import com.moaguide.domain.news.News;
import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.NewsService;
import com.moaguide.service.view.NewsViewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/news/")
@AllArgsConstructor
@Slf4j
public class NewsRestController {
    private final NewsService newsService;
    private final NewsViewService newsViewService;
    private final JWTUtil jwtUtil;

    // 뉴스 홈페이지
    @GetMapping()
    public ResponseEntity<List<NewsCustomDto>> newsHome() {
        List<NewsCustomDto> newsData = newsService.findNews();
        return ResponseEntity.ok().body(newsData);
    }

    // 카테고리별 뉴스 조회
    @GetMapping("/{category}")
    public ResponseEntity<?> newsListByCategory(@PathVariable String category,
                                                @RequestParam String sort,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        Page<NewsCustomDto> newsList;
        // 인기순
        if (sort.equals("popular")) {
            newsList = newsService.getAllByViews(page, size, category);
        }
        // 최신순
        else {
            newsList = newsService.getAllByLatest(page, size, category);
        }
        return ResponseEntity.ok().body(newsList);
    }

    @PostMapping("{NewsId}")
    public ResponseEntity<?> detail_check(@PathVariable Long NewsId, @RequestHeader(value = "Authorization", required = false) String jwt) {
        String nickname = "null";

        // JWT가 존재하는지, 그리고 유효한지 확인
        if (jwt != null && jwt.startsWith("Bearer ")) {
            String token = jwt.substring(7); // "Bearer " 제거한 토큰
            if (!jwtUtil.isExpired(token)) {
                // 토큰이 유효한 경우 닉네임 추출
                nickname = jwtUtil.getNickname(token);
            } else {
                // 토큰이 만료된 경우
                return ResponseEntity.status(401).body("토큰이 만료되었습니다. 다시 로그인 해주세요.");
            }
        }

        try {
            // 닉네임과 관계없이 서비스 호출
            newsViewService.insert(NewsId, nickname);
            newsService.viewupdate(NewsId);
            return ResponseEntity.ok("조회수 추가 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("조회수 추가 실패: " + e.getMessage());
        }
    }

}
