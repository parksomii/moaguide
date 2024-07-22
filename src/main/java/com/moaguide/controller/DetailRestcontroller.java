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
}
