package com.moaguide.controller;

import com.moaguide.dto.SearchLogDto;
import com.moaguide.dto.SearchResponseDto;
import com.moaguide.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class SearchRestController {
    private SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String keyword) {
        searchService.savekeyword(keyword);
        SearchResponseDto dto = searchService.searchAll(keyword);
        return ResponseEntity.ok(dto);
    }

//    @GetMapping("/search/log")
//    public ResponseEntity<?> searchLog(){
//        List<SearchLogDto> dto = searchService.searchrank();
//        return ResponseEntity.ok(dto);
//    }
}