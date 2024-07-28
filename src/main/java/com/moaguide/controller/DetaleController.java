package com.moaguide.controller;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.MusicDetailDto;
import com.moaguide.dto.SummaryListDto;
import com.moaguide.service.DivideService;
import com.moaguide.service.MusicDetailService;
import com.moaguide.service.SummaryListService;
import com.moaguide.service.TransactionService;
import com.moaguide.service.building.BuildingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/summary/detail")
public class DetaleController {
    private final TransactionService transactionService;
    private final SummaryListService summaryListService;
    private final DivideService divideService;
    private final BuildingService buildingService;
    private final MusicDetailService musicDetailService;

    @GetMapping("/building/{id}")
    public String detalehome(@PathVariable String id, Model model) {
        List<Transaction> transaction = transactionService.findtwoByproductId(id);
        Divide divides = divideService.findByproductId(id);
        SummaryListDto summaryListDto = summaryListService.Building(transaction,divides);
        model.addAttribute("building", summaryListDto);
        return "detail/Buildingdetail";
    }

    @GetMapping("/music/{id}")
    public String musicdetail(@PathVariable String id, Model model) {
        List<Transaction> transaction = transactionService.findtwoByproductId(id);
        Divide divides = divideService.findOne(id);
        SummaryListDto summaryListDto =new SummaryListDto(transaction,divides);
        MusicDetailDto musicDetail = musicDetailService.detail(id);
        model.addAttribute("music", summaryListDto);
        model.addAttribute("keyword", musicDetail.getKeyword());
        return "detail/Musicdetail";
    }
}
