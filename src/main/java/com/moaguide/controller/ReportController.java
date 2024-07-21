package com.moaguide.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/report")
public class ReportController {

    @GetMapping
    public String reportPage(){
        return "Article_Report/report";
    }
}
