package com.moaguide.refactor.product.controller;

import com.moaguide.dto.NewDto.DetailDivideResponseDto;
import com.moaguide.dto.NewDto.DetailNoticeResponseDto;
import com.moaguide.dto.NewDto.DetailReportResponseDto;
import com.moaguide.dto.NewDto.DetailTransactionResponseDto;
import com.moaguide.dto.NewDto.customDto.DetailNewsResponseDto;
import com.moaguide.dto.NewDto.customDto.DivideCustomDto;
import com.moaguide.dto.NewDto.customDto.DivideGraphDto;
import com.moaguide.dto.NewDto.customDto.NoticeDto;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import com.moaguide.refactor.news.dto.NewsCustomDto;
import com.moaguide.refactor.news.service.NewsService;
import com.moaguide.refactor.product.service.CurrentDivideService;
import com.moaguide.refactor.product.service.DivideService;
import com.moaguide.refactor.product.service.ReportService;
import com.moaguide.refactor.product.service.TransactionService;
import com.moaguide.service.NoticeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	@GetMapping("/divide/{product_Id}")
	public ResponseEntity<Object> divide(@PathVariable String product_Id, @RequestParam int month) {
		List<DivideCustomDto> divideCustomDtos = divideService.getAllProductIdByDate(product_Id,
			month);
		// null 체크
		if (divideCustomDtos == null) {
			Map<String, Object> response = new HashMap<>();
			response.put("divide", new ArrayList<>());
			return ResponseEntity.ok(response);
		}

		// 빈 리스트 체크
		if (divideCustomDtos.isEmpty()) {
			Map<String, Object> response = new HashMap<>();
			response.put("divide", new ArrayList<>());
			return ResponseEntity.ok(response);
		}

		// 정상적인 경우 데이터 반환
		return ResponseEntity.ok().body(new DetailDivideResponseDto(divideCustomDtos));
	}

	@GetMapping("/divides/{product_Id}")
	public ResponseEntity<Object> new_divide(@PathVariable String product_Id,
		@RequestParam int month) {
		DivideGraphDto divideCustomDtos = divideService.getGraphData(product_Id, month);
		// 정상적인 경우 데이터 반환
		return ResponseEntity.ok().body(divideCustomDtos);
	}

	@GetMapping("/transaction/{product_Id}")
	public ResponseEntity<Object> transaction(@PathVariable String product_Id,
		@RequestParam int month) {
		DetailTransactionResponseDto transaction = transactionService.findbymonth(product_Id,
			month);
		if (transaction.getTransaction() == null) {
			Map<String, Object> response = new HashMap<>();
			response.put("transaction", new ArrayList<>());
			return ResponseEntity.ok(response);
		}

		// 빈 리스트 체크
		if (transaction.getTransaction().isEmpty()) {
			Map<String, Object> response = new HashMap<>();
			response.put("transaction", new ArrayList<>());
			return ResponseEntity.ok(response);
		}

		return ResponseEntity.ok().body(transaction);
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
