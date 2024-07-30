package com.moaguide.service;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.divide.MusicDivide;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.SummaryDto;
import com.moaguide.dto.SummaryListDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SummaryListService {


    public List<SummaryListDto> getSummaryListDto(List<Transaction> transactions, List<Divide> divide,List<List<Transaction>> graph) {
        List<SummaryListDto> summaryListDtos = new ArrayList<>();
        int i = 0;
        for(i=0;i<transactions.size();i++){
            if(divide.isEmpty()){
                summaryListDtos.add(new SummaryListDto(transactions.get(i), graph.get(i)));
            }else{
                summaryListDtos.add(new SummaryListDto(transactions.get(i),divide.get(i), graph.get(i)));
            }

        }
        return summaryListDtos;
    }

    public SummaryListDto Building(List<Transaction> transactions,Divide divide) {
        SummaryListDto summaryListDtos = new SummaryListDto(transactions,divide);
        return summaryListDtos;
    }
 }
