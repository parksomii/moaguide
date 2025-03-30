package com.moaguide.refactor.product.controller;

import static com.moaguide.refactor.util.EmptyCheckUtil.isEmpty;
import static com.moaguide.refactor.util.EmptyCheckUtil.isListEmpty;
import static com.moaguide.refactor.util.HashMapUtil.createEmptyHashMap;

import com.moaguide.refactor.product.dto.DetailTransactionResponseDto;
import com.moaguide.refactor.product.dto.DivideGraphDto;
import com.moaguide.refactor.product.service.DivideService;
import com.moaguide.refactor.product.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 상품 가격 관련 컨트롤러
 * divide는 배당금 관련 컨트롤러
 * transaction은 주가 관련 컨트롤러
 * @author 이준호
 */
@RequestMapping("/detail")
@AllArgsConstructor
@RestController
public class ProductPriceController {
	private final DivideService divideService;
	private final TransactionService transactionService;


	@GetMapping("/divide/{product_Id}")
	public ResponseEntity<Object> divide(@PathVariable String product_Id, @RequestParam int month) {
		DivideGraphDto divideCustomDtos = divideService.getDivideData(product_Id, month);

		if (isEmpty(divideCustomDtos) || isListEmpty(divideCustomDtos.getDivide())) {
			return ResponseEntity.ok(createEmptyHashMap("divide"));
		}

		return ResponseEntity.ok().body(divideCustomDtos);
	}

	@GetMapping("/transaction/{product_Id}")
	public ResponseEntity<Object> transaction(@PathVariable String product_Id,
		@RequestParam int month) {
		DetailTransactionResponseDto transaction = transactionService.getTransactionData(product_Id, month);
		if (isEmpty(transaction) || isEmpty(transaction.getTransaction())) {
			return ResponseEntity.ok(createEmptyHashMap("transaction"));
		}
		return ResponseEntity.ok().body(transaction);
	}
}
