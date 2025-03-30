package com.moaguide.refactor.product.controller;

import com.moaguide.refactor.product.dto.DetailTransactionResponseDto;
import com.moaguide.refactor.product.dto.DivideGraphDto;
import com.moaguide.refactor.product.service.DivideService;
import com.moaguide.refactor.product.service.TransactionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/detail")
@AllArgsConstructor
public class ProductPriceController {
	private final DivideService divideService;
	private final TransactionService transactionService;


	@GetMapping("/divide/{product_Id}")
	public ResponseEntity<Object> divide(@PathVariable String product_Id, @RequestParam int month) {
		DivideGraphDto divideCustomDtos = divideService.getGraphData(product_Id,
			month);
		// null 체크
		if (divideCustomDtos == null) {
			Map<String, Object> response = new HashMap<>();
			response.put("divide", new ArrayList<>());
			return ResponseEntity.ok(response);
		}

		// 빈 리스트 체크
		if (divideCustomDtos.getDivide().isEmpty()) {
			Map<String, Object> response = new HashMap<>();
			response.put("divide", new ArrayList<>());
			return ResponseEntity.ok(response);
		}

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
}
