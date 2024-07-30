package com.moaguide.controller;


import com.moaguide.dto.NewDto.SummaryResponseDto;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.PageRequestDTO;
import com.moaguide.service.SummaryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                                          @RequestParam int page,@RequestParam int size) {
        log.info("category: " + category);
        if(sort.equals("views")) {
            List<SummaryCustomDto> summary = summaryService.getSummaryView(page,size, category);
            return ResponseEntity.ok(new SummaryResponseDto());
        } else if(sort.equals("name")) {
            List<SummaryCustomDto> summary = summaryService.getSummaryName(page,size, category);
            return ResponseEntity.ok(new SummaryResponseDto());
        } else if(sort.equals("divide")) {
            List<SummaryCustomDto> summary = summaryService.getSummaryDvide(page,size, category);
            return ResponseEntity.ok(new SummaryResponseDto());
        } else{
            return ResponseEntity.badRequest().build();
        }
    }
}
