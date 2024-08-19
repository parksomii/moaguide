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
    // todo: 임시로 이미지 넣음.
/*    @GetMapping("/")
    public ResponseEntity<Object> reportList() {
        // 1개만 가져오기
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "date"));
        List<ReportCustomDto> reportList = reportService.getMainReport(pageable);
        return ResponseEntity.ok(reportList);
    }*/

    // 카테고리별 리포트 조회
    @GetMapping("/list/{category}")
    public ResponseEntity<Object> reportListCategory(@PathVariable String category,
                                                     @RequestParam int page,
                                                     @RequestParam int size,
                                                     @RequestParam String subcategory,
                                                     @RequestParam String sort) {
        Page<ReportCustomDto> reportList;
        if (sort.equals("popular")) {
            reportList = reportService.getAllPopularBySubCategory(category, subcategory, page, size);
        } else {
            reportList = reportService.getAllLatestBySubCategory(category, subcategory, page, size);
            log.info("reportList: {}", reportList);
        }
        log.info("******reportList: {}", reportList);

        return ResponseEntity.ok(reportList);
    }

    // 리포트 상세
    @GetMapping("/{report_Id}")
    public ResponseEntity<Object> reportDetail(@PathVariable int report_Id) {
        ReportCustomDto report = reportService.getReportDetail(report_Id);
        return ResponseEntity.ok(report);
    }

    // 리포트 조회수
    @PostMapping("/{report_Id}")
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
