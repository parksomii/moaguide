package com.moaguide.service;

import com.moaguide.domain.GenericRepository;
import com.moaguide.dto.NewDto.customDto.IssueCustomDto;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.NewDto.customDto.finishCustomDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {
    private final GenericRepository genericRepository;

    @Transactional(readOnly = false)
    public List<SummaryCustomDto> getlist(int page, int size, String sort) {
        return genericRepository.findCustomList(page,size,sort);

    }

    public List<SummaryCustomDto> getcategorylist (int page, int size, String sort, String category) {
        return genericRepository.findCustomListCategory(page,size,sort,category);
    }

    public List<IssueCustomDto> getreadylist(int page, int size, String category) {
        LocalDate localDate = LocalDate.now(); // 현재 날짜 가져오기
        Date sqlDate = Date.valueOf(localDate); // LocalDate를 java.sql.Date로 변환
            if(category.equals("all")) {
                return genericRepository.findCustomIssue(page,size,sqlDate);
            }else {
                return genericRepository.findCustomIssueCategory(page, size, sqlDate, category);
            }
    }

    public List<IssueCustomDto> getstartlisty(int page, int size, String category) {
        LocalDate localDate = LocalDate.now(); // 현재 날짜 가져오기
        Date sqlDate = Date.valueOf(localDate); // LocalDate를 java.sql.Date로 변환
        if(category.equals("all")) {
            return genericRepository.findCustomStart(page,size,sqlDate);
        }else {
            return genericRepository.findCustomStartCategory(page, size, sqlDate, category);
        }
    }

    public List<finishCustomDto> getfinish(int page, int size, String category) {
        if(category.equals("all")){
            return genericRepository.findfinish(page,size);
        }else {
            return genericRepository.findfinishCategory(page,size,category);
        }
    }

    public List<finishCustomDto> getend(int page, int size , String category) {
        if(category.equals("all")){
            return genericRepository.findend(page,size);
        }else {
            return genericRepository.findendCategory(page, size, category);
        }
    }

}
