package com.moaguide.controller;

import com.moaguide.dto.NewDto.SummaryResponseDto;
import com.moaguide.dto.NewDto.customDto.*;
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
@RequestMapping("/summary/")
public class SummaryRestController {
    private final ReportService reportService;
    private final ProductService productService;

    // 카테고리별 상품현황 목록 조회
    @GetMapping("/list/{category}")
    public ResponseEntity<?> summary(@PathVariable("category") String category,
                                          @RequestParam String subcategory,
                                          @RequestParam String sort,
                                          @RequestParam int page,
                                          @RequestParam int size) {
        page = page-1;
        if(subcategory.equals("trade")){
            List<SummaryCustomDto> summary;
            if(category.equals("all")){
                summary = productService.getlist(page,size,sort);
                return ResponseEntity.ok().body(new SummaryResponseDto(summary,page,size));
            }else{
                summary = productService.getcategorylist(page,size,sort,category);
                return ResponseEntity.ok(new SummaryResponseDto(summary,page,size));
            }
        } else if (subcategory.equals("start")) {
            List<IssueCustomDto> inavete;
            if(sort.equals("ready")){
                inavete = productService.getreadylist(page,size,category);
                return ResponseEntity.ok(new SummaryResponseDto(inavete,page,size));
            }else if(sort.equals("start")){
                inavete = productService.getstartlisty(page,size,category);
                return ResponseEntity.ok(new SummaryResponseDto(inavete,page,size));
            }
        } else if (subcategory.equals("end")){
            List<finishCustomDto> inavete;
            if(sort.equals("end")){
                inavete = productService.getfinish(page,size,category);
                return ResponseEntity.ok((new SummaryResponseDto(inavete,page,size)));
            }else if(sort.equals("finish")){
                inavete = productService.getend(page,size,category);
                return ResponseEntity.ok((new SummaryResponseDto(inavete,page,size)));
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