package com.moaguide.controller;


import com.moaguide.service.SummaryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/summary/")
public class SummaryRestController {

/*    @GetMapping("/list/{category}")
    public ResponseEntity<Object> summary(@PathVariable String category, @RequestParam int page, @RequestParam int size) {
        return null;
    }*/
}
