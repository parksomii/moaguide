package com.moaguide.controller;


import com.moaguide.domain.report.Report;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import com.moaguide.dto.PageRequestDTO;
import com.moaguide.service.ReportService;
import com.moaguide.service.view.ReportViewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/report")
@AllArgsConstructor
@Slf4j
public class ReportRestController {
    private ReportService reportService;
    private ReportViewService reportViewService;

    // 리포트 홈페이지
    @GetMapping("/")
    public ResponseEntity<Object> reportList() {
        // 1개만 가져오기
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "date"));
        List<ReportCustomDto> reportList = reportService.getMainReport(pageable);
        return ResponseEntity.ok(reportList);
    }

    // 카테고리별 리포트 조회
    @GetMapping("/{category}")
    public ResponseEntity<Object> reportListCategory(@PathVariable String category,
                                                     @RequestParam(required = false, defaultValue = "guide") String subcategory,
                                                     @RequestParam(required = false, defaultValue = "latest") String sort,
                                                     PageRequestDTO pageRequestDTO) {
        // 유효한 서브 카테고리 목록
        if (!isValidSubcategory(subcategory)) {
            return ResponseEntity.badRequest().body(null);
        }

        // 정렬 방향 설정
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize(),
                sort.equals("popular") ? Sort.by(Sort.Direction.DESC, "view") : Sort.by(Sort.Direction.DESC, "date"));

        Page<ReportCustomDto> reportList;
        if (sort.equals("popular")) {
            reportList = reportService.getAllPopularBySubCategory(category, subcategory, pageable);
        } else {
            reportList = reportService.getAllLatestBySubCategory(category, subcategory, pageable);
            log.info("reportList: {}", reportList);
        }
        log.info("******reportList: {}", reportList);

        return ResponseEntity.ok(reportList);
    }

    // 서브 카테고리 유효성 검사
    private boolean isValidSubcategory(String subcategory) {
        return "analysis".equals(subcategory) || "situation".equals(subcategory) || "guide".equals(subcategory);
    }

    // 리포트 상세

    // 리포트 조회수
    @PostMapping("/view/{report_Id}")
    public ResponseEntity.HeadersBuilder<ResponseEntity.BodyBuilder> detail_check(@PathVariable int report_Id, @RequestHeader("Local-Storage-Key") String localStorageKey, @RequestHeader("Local-date") String date){
        Report report = reportService.findById(report_Id);
        String response = reportViewService.insert(report,localStorageKey,date);
        if(response != null) {
            return ResponseEntity.ok();
        }else{
            return ResponseEntity.badRequest();
        }
    }

}
