package com.moaguide.controller;

import com.moaguide.dto.NewDto.SummaryResponseDto;
import com.moaguide.dto.NewDto.customDto.ArticleSummaryDto;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.NewDto.customDto.SummaryDivideCustomDto;
import com.moaguide.dto.NewDto.customDto.SummaryRecentDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.BookmarkService;
import com.moaguide.service.CurrentDivideService;
import com.moaguide.service.ProductService;
import com.moaguide.service.StudyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/summary")
public class SummaryRestController {
    private final StudyService articleService;
    private final ProductService productService;
    private final CurrentDivideService divideService;
    private final BookmarkService bookmarkService;
    private final JWTUtil jwtUtil;

    @PostMapping("/bookmark/{productId}")
    public ResponseEntity<String> bookmark(@PathVariable String productId, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ") && !jwtUtil.isExpired(jwt.substring(7))) {
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            try {
                bookmarkService.postBookmark(productId, nickname);
                return ResponseEntity.ok("북마크 성공");
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body("북마크 실패: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("유효하지 않은 토큰");
        }
    }

    @DeleteMapping("/bookmark/{productId}")
    public ResponseEntity<String> bookmarkDelete(@PathVariable String productId, HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ") && !jwtUtil.isExpired(jwt.substring(7))) {
            String nickname = jwtUtil.getNickname(jwt.substring(7));

            try {
                bookmarkService.deleteBookmark(productId, nickname);
                return ResponseEntity.ok().body("북마크 해제 성공");
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body("북마크 해제 실패: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("유효하지 않은 토큰");
        }
    }


    @GetMapping
    public ResponseEntity<SummaryRecentDto> getSummary() {
        Pageable pageable = PageRequest.of(0, 3);
        List<SummaryDivideCustomDto> divide = divideService.findrecent(pageable);
        List<SummaryCustomDto> customDtos = productService.getlist(0,3,"lastDivide_rate desc","null");
        return ResponseEntity.ok(new SummaryRecentDto(divide,customDtos));
    }

    // 카테고리별 상품현황 목록 조회
    @GetMapping("/list")
    public ResponseEntity<?> summary(@RequestParam String category,
                                          @RequestParam String subcategory,
                                          @RequestParam String sort,
                                          @RequestParam int page,
                                          @RequestParam int size) {
        page = page-1;
        String nickname = "moaguide";
        if(subcategory.equals("trade")){
            List<SummaryCustomDto> summary;
            if(category.equals("all")){
                summary = productService.getlist(page,size,sort,nickname);
                int total =  productService.getlistTotal("거래중");
                return ResponseEntity.ok().body(new SummaryResponseDto(summary,page,size,total));
            }else if(category.equals("bookmark")){
                summary = productService.getlistBookmark(page,size,sort,nickname);
                int total =  productService.getlistTotalByBookmark("거래중",nickname);
                return ResponseEntity.ok().body(new SummaryResponseDto(summary,page,size,total));
            }else{
                summary = productService.getcategorylist(page,size,sort,category,nickname);
                int total = productService.getlistTotalCategory("거래중",category);
                return ResponseEntity.ok(new SummaryResponseDto(summary,page,size,total));
            }
        } else if (subcategory.equals("start")) {
            SummaryResponseDto inavete;
            if(sort.equals("ready")){
                inavete = productService.getreadylist(page,size,category,nickname);
                return ResponseEntity.ok(inavete);
            }else if(sort.equals("start")){
                inavete = productService.getstartlisty(page,size,category,nickname);
                return ResponseEntity.ok(inavete);
            }
        } else if (subcategory.equals("end")){
            SummaryResponseDto inavete;
            if(sort.equals("end")){
                inavete = productService.getend(page,size,category,nickname);
                return ResponseEntity.ok(inavete);
            }else if(sort.equals("finish")){
                inavete = productService.getfinish(page,size,category,nickname);
                return ResponseEntity.ok(inavete);
            }
        }else if(subcategory.equals("bookmark") && sort.equals("bookmark")){
            SummaryResponseDto invate;
            invate = bookmarkService.getProductBybookmark(category,nickname,page,size);
            return ResponseEntity.ok(invate);
        }
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

    // 관련 리포트
    @GetMapping("/report/{category}")
    public ResponseEntity<Object> summaryReport(@PathVariable("category") String category) {
        Pageable pageable = PageRequest.of(0, 3);
        List<ArticleSummaryDto> reportList = articleService.getSummary( pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("article", reportList);
        return ResponseEntity.ok(response);
    }

}