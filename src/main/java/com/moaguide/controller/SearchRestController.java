package com.moaguide.controller;

import com.moaguide.dto.SearchLogDto;
import com.moaguide.dto.SearchResponseDto;
import com.moaguide.dto.searchCategoryDto;
import com.moaguide.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class SearchRestController {
    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String keyword) {
        try {
            searchService.savekeyword(keyword);  // 키워드 저장
            List<searchCategoryDto> dto = searchService.searchAll(keyword);  // 검색 수행
            return ResponseEntity.ok(dto);  // 성공 응답
        } catch (IOException e) {
            // IOException이 발생하면 적절한 에러 메시지와 함께 500 에러를 반환
            return ResponseEntity.status(500).body("Elasticsearch와 통신 중 오류가 발생했습니다.");
        } catch (Exception e) {
            // 그 외 다른 예외가 발생한 경우 처리
            return ResponseEntity.status(500).body("알 수 없는 오류가 발생했습니다.");
        }
    }
//    @GetMapping("/search/log")
//    public ResponseEntity<?> searchLog(){
//        List<SearchLogDto> dto = searchService.searchrank();
//        return ResponseEntity.ok(dto);
//    }
}