package com.moaguide.controller;


import com.moaguide.domain.report.Report;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import com.moaguide.refactor.product.service.ReportService;
import com.moaguide.service.ReportViewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/report")
@AllArgsConstructor
@Slf4j
public class ReportRestController {
    private ReportService reportService;
    private ReportViewService reportViewService;

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
