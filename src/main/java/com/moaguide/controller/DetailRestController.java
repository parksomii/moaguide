package com.moaguide.controller;

import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.product.Product;
import com.moaguide.domain.report.Report;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.MusicDetailDto;
import com.moaguide.dto.NewDto.*;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.dto.NewsDto;
import com.moaguide.service.*;
import com.moaguide.service.building.BuildingService;
import com.moaguide.service.view.ProductViewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/")
public class DetailRestController {
    private final ProductService productService;
    private final ReportService reportService;
    private final NewsService newsService;
    private final DivideService divideService;
    private final TransactionService transactionService;
    private final NoticeService noticeService;
    private final CurrentDivideService currentDivideService;

    @GetMapping("report/{category}")
    public ResponseEntity<Object> report(@PathVariable String category,@RequestParam int page,@RequestParam int size,@RequestParam String subCategory) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReportCustomDto> report = reportService.findBylist(category,subCategory,pageable);
        return ResponseEntity.ok(new DetailReportResponseDto(report));
    }

    @GetMapping("news/")
    public ResponseEntity<Object> news(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NewsCustomDto> newsDtos = newsService.findBydetail(keyword,pageable);
        return ResponseEntity.ok(new DetailNewsResponseDto(newsDtos));
    }

    @GetMapping("divide/building")
    public ResponseEntity<Object> divide(@RequestParam String product_Id) {
        List<DivideCustomDto> divideCustomDtos = divideService.findAllByproductId(product_Id);
        Integer divideCycle = currentDivideService.findCycle(product_Id);
        return ResponseEntity.ok().body(new DetailDivideResponseDto(divideCustomDtos,divideCycle));
    }

    @GetMapping("transaction/{product_Id}")
    public ResponseEntity<Object> transaction(@PathVariable String product_Id,@RequestParam int month){
        List<TransactionDto> transaction = transactionService.findbymonth(product_Id,month);
        return ResponseEntity.ok().body(new DetailTransactionResponseDto(transaction));
    }

    @GetMapping("notice/list/{product_Id}")
    public ResponseEntity<Object> notice(@PathVariable String product_Id,@RequestParam int page,@RequestParam int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<NoticeDto> noticeDtos = noticeService.findbyProductId(product_Id,pageable);
        return ResponseEntity.ok(new DetailNoticeResponseDto(noticeDtos));
    }

    @GetMapping("notice/{notice_Id}")
    public ResponseEntity<Object> notice(@PathVariable long notice_Id){
        NoticeDto noticeDto = noticeService.findBydetail(notice_Id);
        return ResponseEntity.ok().body(noticeDto);
    }



//    @PostMapping("{product_Id}")
//    public ResponseEntity.HeadersBuilder<ResponseEntity.BodyBuilder> detail_check(@PathVariable String product_Id, @RequestHeader("Local-Storage-Key") String localStorageKey, @RequestHeader("Local-date") String date){
//
//    }
}
