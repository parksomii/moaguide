package com.moaguide.controller;

import com.moaguide.service.SearchService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class SearchRestController {
    private SearchService searchService;

//    @GetMapping("/search")
//    public String search(@RequestParam String keyword) {
//
//    }
}
