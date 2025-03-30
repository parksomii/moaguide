package com.moaguide.refactor.product.controller;

import com.moaguide.refactor.news.dto.NewsCustomDto;
import com.moaguide.refactor.news.service.NewsService;
import com.moaguide.refactor.product.dto.DetailNewsResponseDto;
import com.moaguide.refactor.product.dto.DetailNoticeResponseDto;
import com.moaguide.refactor.product.dto.DetailReportResponseDto;
import com.moaguide.refactor.product.dto.NoticeDto;
import com.moaguide.refactor.product.dto.ReportCustomDto;
import com.moaguide.refactor.product.service.CurrentDivideService;
import com.moaguide.refactor.product.service.DivideService;
import com.moaguide.refactor.product.service.ReportService;
import com.moaguide.refactor.product.service.TransactionService;
import com.moaguide.service.NoticeService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<Object> report(@PathVariable String category, @RequestParam int page,
		@RequestParam int size, @RequestParam String subCategory) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ReportCustomDto> report = reportService.findBylist(category, subCategory, pageable);
		return ResponseEntity.ok(new DetailReportResponseDto(report));
	}

	@GetMapping("/news/{product_Id}")
	public ResponseEntity<Object> news(@PathVariable String product_Id, @RequestParam int page,
		@RequestParam int size) {
		List<NewsCustomDto> newsDtos = newsService.findBydetail(product_Id, page - 1, size);
		int total = newsService.findByDetailCount(product_Id);
		return ResponseEntity.ok(new DetailNewsResponseDto(newsDtos, page, size, total));
	}

	@GetMapping("/notice/list/{product_Id}")
	public ResponseEntity<Object> notice(@PathVariable String product_Id, @RequestParam int page,
		@RequestParam int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<NoticeDto> noticeDtos = noticeService.findbyProductId(product_Id, pageable);
		return ResponseEntity.ok(new DetailNoticeResponseDto(noticeDtos));
	}

	@GetMapping("/notice/{notice_Id}")
	public ResponseEntity<Object> notice(@PathVariable long notice_Id) {
		NoticeDto noticeDto = noticeService.findBydetail(notice_Id);
		// null 체크
		if (noticeDto == null) {
			return ResponseEntity.badRequest().body("Invalid request: No data found.");
		}
		return ResponseEntity.ok().body(noticeDto);
	}


}
