package com.moaguide.controller;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.divide.MusicDivide;
import com.moaguide.domain.summary.Summary;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.*;
import com.moaguide.service.*;
import com.moaguide.service.Music.MusicDivideService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/summary")
public class SummaryController {
    private final SummaryService summaryService;
    private final LoginService loginService;
    private final TransactionService transactionService;
    private final DivideService divideService;
    private final SummaryListService summaryListService;

    @GetMapping
    public String summaryhome(Model model){
        List<Divide> divides = divideService.findTwo();
        List<Summary> summaries = summaryService.findByViews();
        List<SummaryListDto> summaryListDtos = new ArrayList<>();
//        for(Summary summary : summaries){
//            List<Transaction> transaction = transactionService.findtwoByproductId(summary.getProductId());
//            Divide divide = divideService.findlastByPrductId(summary.getProductId());
//            summaryListDtos.add(new SummaryListDto(transaction,divide));
//        }
        for(int i = 0; i < 2; i++){
            List<Transaction> transaction = transactionService.findtwoByproductId(summaries.get(i).getProductId());
            Divide divide = divideService.findlastByPrductId(summaries.get(i).getProductId());
            summaryListDtos.add(new SummaryListDto(transaction,divide));
        }
        model.addAttribute("divides", divides);
        model.addAttribute("summaries", summaryListDtos);
        return "summary/summary";
    }

    @GetMapping("/list/all")
    public String summarylist(Model model, PageRequestDTO pageRequestDTO){
        log.info("*************** SummaryController GET /summary"+ pageRequestDTO);
        Page<Summary> result = summaryService.findAll(pageRequestDTO);
        log.info("*************** SummaryController GET /result"+ result);
        List<SummaryDto> summaryDtoList = result.getContent()
                .stream()
                .map(summary -> summary.toDto())
                .toList();
        log.info("*************** SummaryController GET /summaryDtoList"+ summaryDtoList);
        List<Transaction> transactions = transactionService.findAllByid(summaryDtoList);
        log.info("*************** SummaryController GET /transactions" + transactions);
        List<Divide> divide = divideService.findByID(summaryDtoList);
        log.info("*************** SummaryController GET /divide" + divide);
        List<List<Transaction>> graph = transactionService.graph(summaryDtoList);
        log.info("*************** SummaryController GET /graph" + graph);
        List<SummaryListDto> summaryListDtos = summaryListService.getSummaryListDto(transactions,divide,graph);
        log.info("*************** SummaryController GET /summaryListDtos" + summaryListDtos);
        PageResponseDTO pageResponseDTO = new PageResponseDTO(summaryListDtos,pageRequestDTO, result.getTotalElements());

        String id = loginService.loginId();
        String role = loginService.loginRole();
        model.addAttribute("Id", id);
        model.addAttribute("role",role);
        model.addAttribute("pageDTO", pageResponseDTO);
        model.addAttribute("transactions", summaryListDtos);
        model.addAttribute("keyword","all");
        return "summary/summarylist";
    }

    @GetMapping("/list/{category}")
    public String summarycategorylist(Model model, @PathVariable String category, PageRequestDTO pageRequestDTO){
        if(category.equals("cow")||category.equals("art")||category.equals("content")){
            return "ready";
        }else {
            log.info("*************** SummaryController GET /summary" + pageRequestDTO);
            Page<Summary> result = summaryService.findlistByCategory(category, pageRequestDTO);
            log.info("*************** SummaryController GET /result" + result);
            List<SummaryDto> summaryDtoList = result.getContent()
                    .stream()
                    .map(summary -> summary.toDto())
                    .toList();
            log.info("*************** SummaryController GET /summaryDtoList" + summaryDtoList);
            List<Transaction> transactions = transactionService.findAllByid(summaryDtoList);
            log.info("*************** SummaryController GET /transactions" + transactions);
            List<Divide> divide = divideService.findByID(summaryDtoList);
            log.info("*************** SummaryController GET /divide" + divide);
            List<List<Transaction>> graph = transactionService.graph(summaryDtoList);
            log.info("*************** SummaryController GET /graph" + graph);
            List<SummaryListDto> summaryListDtos = summaryListService.getSummaryListDto(transactions, divide, graph);
            log.info("*************** SummaryController GET /summaryListDtos" + summaryListDtos);
            PageResponseDTO pageResponseDTO = new PageResponseDTO(summaryListDtos, pageRequestDTO, result.getTotalElements());

            String id = loginService.loginId();
            String role = loginService.loginRole();
            model.addAttribute("Id", id);
            model.addAttribute("role", role);
            model.addAttribute("pageDTO", pageResponseDTO);
            model.addAttribute("transactions", summaryListDtos);
            model.addAttribute("keyword",category);
            return "summary/summarylist";
        }
    }
}
