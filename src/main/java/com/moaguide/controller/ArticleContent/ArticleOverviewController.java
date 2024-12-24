package com.moaguide.controller.ArticleContent;

import com.moaguide.domain.CategoryContent.Category;
import com.moaguide.dto.ArticleOverviewDto;
import com.moaguide.service.ArticleContent.ArticleOverviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/contents")
@AllArgsConstructor
@RestController
@Slf4j
public class ArticleOverviewController {

    private final ArticleOverviewService articleOverviewService;

    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getContentsOverview(
            @RequestParam(defaultValue = "4") int popularLimit,
            @RequestParam(defaultValue = "4") int recentLimit,
            @RequestParam(defaultValue = "5") int newsLimit) {

        // 인기 콘텐츠 가져오기
        List<ArticleOverviewDto> popularContents = articleOverviewService.getPopularContents(popularLimit);

        // 최신 콘텐츠 가져오기
        List<ArticleOverviewDto> recentContents = articleOverviewService.getRecentContents(recentLimit);

        // 최신 뉴스 클리핑 가져오기 (카테고리 = NEWS)
        List<ArticleOverviewDto> latestNews = articleOverviewService.getLatestEconomicIssues(Category.NEWS, newsLimit);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("popularContents", popularContents);
        response.put("recentContents", recentContents);
        response.put("latestNewsClipping", latestNews);

        return ResponseEntity.ok(response);
    }
}