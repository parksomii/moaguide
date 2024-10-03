package com.moaguide.controller;

import com.moaguide.dto.NewDto.*;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail")
public class DetailRestController {
    private final ReportService reportService;
    private final NewsService newsService;
    private final DivideService divideService;
    private final TransactionService transactionService;
    private final NoticeService noticeService;
    private final CurrentDivideService currentDivideService;

    @GetMapping("/report/{category}")
    public ResponseEntity<Object> report(@PathVariable String category,@RequestParam int page,@RequestParam int size,@RequestParam String subCategory) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReportCustomDto> report = reportService.findBylist(category,subCategory,pageable);
        return ResponseEntity.ok(new DetailReportResponseDto(report));
    }

    @GetMapping("/news/{product_Id}")
    public ResponseEntity<Object> news(@PathVariable String product_Id, @RequestParam int page, @RequestParam int size) {
        List<NewsCustomDto> newsDtos = newsService.findBydetail(product_Id,page-1,size);
        int total = newsService.findByDetailCount(product_Id);
        return ResponseEntity.ok(new DetailNewsResponseDto(newsDtos,page,size,total));
    }

    @GetMapping("/divide/{product_Id}")
    public ResponseEntity<Object> divide(@PathVariable String product_Id, @RequestParam int month) {
        List<DivideCustomDto> divideCustomDtos = divideService.getAllProductIdByDate(product_Id,month);
        // null 체크
        if (divideCustomDtos == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }

        // 빈 리스트 체크
        if (divideCustomDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content available.");
        }

        // 정상적인 경우 데이터 반환
        return ResponseEntity.ok().body(new DetailDivideResponseDto(divideCustomDtos));
    }

    @GetMapping("/transaction/{product_Id}")
    public ResponseEntity<Object> transaction(@PathVariable String product_Id,@RequestParam int month){
        List<TransactionDto> transaction = transactionService.findbymonth(product_Id,month);
        if (transaction == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }

        // 빈 리스트 체크
        if (transaction.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content available.");
        }

        return ResponseEntity.ok().body(new DetailTransactionResponseDto(transaction));
    }

    @GetMapping("/notice/list/{product_Id}")
    public ResponseEntity<Object> notice(@PathVariable String product_Id,@RequestParam int page,@RequestParam int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<NoticeDto> noticeDtos = noticeService.findbyProductId(product_Id,pageable);
        return ResponseEntity.ok(new DetailNoticeResponseDto(noticeDtos));
    }

    @GetMapping("/notice/{notice_Id}")
    public ResponseEntity<Object> notice(@PathVariable long notice_Id){
        NoticeDto noticeDto = noticeService.findBydetail(notice_Id);
        return ResponseEntity.ok().body(noticeDto);
    }


}
