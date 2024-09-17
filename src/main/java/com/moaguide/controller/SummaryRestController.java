package com.moaguide.controller;

import com.moaguide.dto.NewDto.SummaryResponseDto;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.service.CurrentDivideService;
import com.moaguide.service.DivideService;
import com.moaguide.service.ProductService;
import com.moaguide.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/summary")
public class SummaryRestController {
    private final ReportService reportService;
    private final ProductService productService;
    private final CurrentDivideService divideService;
    @GetMapping
    public ResponseEntity<SummaryRecentDto> getSummary() {
        Pageable pageable = PageRequest.of(0, 3);
        List<SummaryDivideCustomDto> divide = divideService.findrecent(pageable);
        List<SummaryCustomDto> customDtos = productService.getlist(0,3,"lastDivide_rate desc");
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
        if(subcategory.equals("trade")){
            List<SummaryCustomDto> summary;
            if(category.equals("all")){
                summary = productService.getlist(page,size,sort);
                int total =  productService.getlistTotal("거래중");
                return ResponseEntity.ok().body(new SummaryResponseDto(summary,page,size,total));
            }else{
                summary = productService.getcategorylist(page,size,sort,category);
                int total = productService.getlistTotalCategory("거래중",category);
                return ResponseEntity.ok(new SummaryResponseDto(summary,page,size,total));
            }
        } else if (subcategory.equals("start")) {
            SummaryResponseDto inavete;
            if(sort.equals("ready")){
                inavete = productService.getreadylist(page,size,category);
                return ResponseEntity.ok(inavete);
            }else if(sort.equals("start")){
                inavete = productService.getstartlisty(page,size,category);
                return ResponseEntity.ok(inavete);
            }
        } else if (subcategory.equals("end")){
            SummaryResponseDto inavete;
            if(sort.equals("end")){
                inavete = productService.getend(page,size,category);
                return ResponseEntity.ok(inavete);
            }else if(sort.equals("finish")){
                inavete = productService.getfinish(page,size,category);
                return ResponseEntity.ok(inavete);
            }
        }
        return ResponseEntity.badRequest().body("잘못된 요청입니다.");
    }

    // 관련 리포트
    @GetMapping("/report/{category}")
    public ResponseEntity<Object> summaryReport(@PathVariable("category") String category,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "size", defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<ReportCustomDto> reportList = reportService.getSummary(category, pageable);
        return ResponseEntity.ok(reportList);
    }

}