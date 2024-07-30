package com.moaguide.controller;


import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.PageRequestDTO;
import com.moaguide.service.SummaryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/summary/")
@Slf4j
public class SummaryRestController {

    private final SummaryService summaryService;

    @GetMapping("/list/{category}")
    public ResponseEntity<Object> summary(@PathVariable("category") String category,
                                          @RequestParam(required = false, defaultValue = "views") String sort,
                                          PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize());
        log.info("category: " + category);
        List<SummaryCustomDto> summary = summaryService.getSummaryList(pageable, category, sort);
        log.info("controller - summary: " + summary);
        return ResponseEntity.ok(summary);
    }
}
