package com.moaguide.controller;

import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.product.Product;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.MusicDetailDto;
import com.moaguide.dto.NewDto.*;
import com.moaguide.dto.NewDto.customDto.*;
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
    private ProductService productService;
    private ReportService reportService;
    private NewsService newsService;
    private DivideService divideService;
    private BuildingService buildingService;
    private TransactionService transactionService;
    private NoticeService noticeService;
    private MusicDetailService musicDetailService;
    private ProductViewService productViewService;

    @GetMapping("report/{category}")
    public ResponseEntity<Object> report(@PathVariable String category,@RequestParam int page,@RequestParam int size,@RequestParam String subCategory) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<ReportCustomDto> Report = reportService.findBylist(category,subCategory,pageable);
        return ResponseEntity.ok(new DetailReportResponseDto(Report));
    }

    @GetMapping("news/")
    public ResponseEntity<Object> news(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<NewsCustomDto> news = newsService.findBydetail(keyword,pageable);
        log.info("뉴스체크 :{}", news);
        return ResponseEntity.ok(new DetailNewsResponseDto(news));
    }

    @GetMapping("divide/{category}")
    public ResponseEntity<Object> divide(@PathVariable String category,@RequestParam String product_Id) {
        List<DivideCustomDto> divideDtos = divideService.findAllByproductId(product_Id);
        if(category.equals("building")) {
            BuildingDetail buildingDetailDto = buildingService.detail(product_Id);
            return ResponseEntity.ok(new DetailDivideResponseDto(divideDtos,buildingDetailDto.getDividendCycle()));
        }else{
            return ResponseEntity.ok(new DetailDivideResponseDto(divideDtos,1));
        }
    }

    @GetMapping("transaction/{product_Id}")
    public ResponseEntity<Object> transaction(@PathVariable String product_Id,@RequestParam int month){
        List<TransactionDto> transactionDtos = transactionService.findbymonth(product_Id,month);
        return ResponseEntity.ok(new DetailTransactionResponseDto(transactionDtos));
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
        return ResponseEntity.ok(noticeDto);
    }

    @GetMapping("{product_Id}")
    public ResponseEntity<Object> detail( @PathVariable String product_Id) {
        List<Transaction> transactionDtos = transactionService.findtwoByproductId(product_Id);
        Divide divideDtos = divideService.findOne(product_Id);
        if(divideDtos.getCategory().equals("building")) {
            BuildingDetail details = buildingService.detail(product_Id);
            return ResponseEntity.ok(new DetailResponseDto(transactionDtos,divideDtos,details));
        }else{
            MusicDetailDto details = musicDetailService.detail(product_Id);
            return ResponseEntity.ok(new DetailResponseDto(transactionDtos,divideDtos,details));
        }
        //if(divideDtos.getCategory().equals("music"))
    }

    @PostMapping("{product_Id}")
    public ResponseEntity.HeadersBuilder<ResponseEntity.BodyBuilder> detail_check(@PathVariable String product_Id, @RequestHeader("Local-Storage-Key") String localStorageKey, @RequestHeader("Local-date") String date){
        Product product = productService.findById(product_Id);
        String response = productViewService.insert(product,localStorageKey,date);
        if(response != null) {
            return ResponseEntity.ok();
        }else{
            return ResponseEntity.badRequest();
        }
    }
}
