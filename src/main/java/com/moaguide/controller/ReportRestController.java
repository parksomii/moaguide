package com.moaguide.controller;


import com.moaguide.domain.report.Report;
import com.moaguide.service.ReportService;
import com.moaguide.service.view.ReportViewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/report")
@AllArgsConstructor
public class ReportRestController {
    private ReportService reportService;
    private ReportViewService reportViewService;

    @PostMapping("{report_Id}")
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
