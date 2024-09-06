package com.moaguide.service;

import com.moaguide.domain.GenericRepository;
import com.moaguide.domain.summary.Product;
import com.moaguide.domain.summary.ProductRepository;
import com.moaguide.dto.NewDto.customDto.IssueCustomDto;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.NewDto.customDto.finishCustomDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final GenericRepository genericRepository;

    public List<SummaryCustomDto> getlist(int page, int size, String sort) {
        List<SummaryCustomDto> summary = genericRepository.findCustomList(page,size,sort);
        return summary;
    }

    public List<SummaryCustomDto> getcategorylist (int page, int size, String sort, String category) {
        List<SummaryCustomDto> summary = genericRepository.findCustomListCategory(page,size,sort,category);
        return summary;
    }

    public List<IssueCustomDto> getstartlistCategory(int page, int size, String sort, String category,String subcategory) {
        LocalDate localDate = LocalDate.now(); // 현재 날짜 가져오기
        Date sqlDate = Date.valueOf(localDate); // LocalDate를 java.sql.Date로 변환
        if(sort.equals("Ready")){
            if(subcategory.equals("all")) {
                List<IssueCustomDto> issue = genericRepository.findCustomIssue(page,size,sqlDate);
                return issue;
            }else {
                List<IssueCustomDto> issue = genericRepository.findCustomIssueCategory(page, size, sqlDate, category);
                return issue;
            }
        }else{
            if(subcategory.equals("all")) {
                List<IssueCustomDto> issue = genericRepository.findCustomStart(page,size,sqlDate);
                return issue;

            }else {
                List<IssueCustomDto> issue = genericRepository.findCustomStartCategory(page, size, sqlDate, category);
                return issue;

            }
        }
    }

    public Product findById(String productId) {
        return productRepository.findById(productId).get();
    }


    public List<finishCustomDto> getfinish(int page, int size, String sort) {
        if(sort.equals("finish")){
            List<finishCustomDto> issue = genericRepository.findfinish(page,size);
            return issue;
        }else {
            List<finishCustomDto> issue = genericRepository.findend(page,size);
            return issue;
        }
    }

    public List<finishCustomDto> getfinishCategory(int page, int size, String sort, String category) {
        if(sort.equals("finish")){
            List<finishCustomDto> issue = genericRepository.findfinishCategory(page,size,category);
            return issue;
        }else {
            List<finishCustomDto> issue = genericRepository.findendCategory(page,size,category);
            return issue;
        }
    }
}
