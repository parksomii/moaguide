package com.moaguide.controller;

import com.moaguide.dto.PageRequestDTO;
import com.moaguide.dto.PageResponseDTO;
import com.moaguide.service.SummaryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final SummaryService summaryService;

    // 카테고리별 상품 목록 조회
    @GetMapping("/list/{category}")
    public ResponseEntity<PageResponseDTO<?>> list(@PathVariable("category") String category, PageRequestDTO pageRequestDTO) {
        log.info("*************** CategoryController GET /category - category : {}", category);
        PageResponseDTO<?> pageResponseDTO;
        if (category.equals("all")) {
            pageResponseDTO = summaryService.findAllByCategory(pageRequestDTO);
        } else {
            pageResponseDTO = summaryService.findByCategory(category, pageRequestDTO);
        }
        log.info("*************** CategoryController GET /category - pageResponseDTO : {}", pageResponseDTO);
        return new ResponseEntity<>(pageResponseDTO, HttpStatus.OK);
    }
}
