package com.moaguide.controller;

import com.moaguide.domain.transaction.Transaction;
import com.moaguide.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/summary/detail/")
public class DetailRestcontroller {
    private final TransactionService transactionService;

    @GetMapping("transaction/{id}")
    public ResponseEntity<Object> transaction(@PathVariable String id, @RequestParam(required = false) String date) {
        LocalDate parsedDate = null;
        if (date != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                parsedDate = LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                log.error("날짜 형식이 잘못되었습니다: {}", date);
                return ResponseEntity.badRequest().body("날짜 형식이 잘못되었습니다.");
            }
        } else {
            parsedDate = LocalDate.now().minusMonths(3); // 기본값 설정 (예: 현재 날짜에서 3개월 전)
        }
        List<Transaction> transactions = transactionService.findbyday(id, parsedDate);
        return ResponseEntity.ok(transactions);
    }
}
