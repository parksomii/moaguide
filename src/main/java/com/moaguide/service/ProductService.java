package com.moaguide.service;

import com.moaguide.domain.GenericRepository;
import com.moaguide.domain.issueprice.IssuePriceRepository;
import com.moaguide.domain.product.ProductRepository;
import com.moaguide.dto.NewDto.SummaryResponseDto;
import com.moaguide.dto.NewDto.customDto.IssueCustomDto;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.NewDto.customDto.endCustomDto;
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
    private final ProductRepository productRepository;
    private final IssuePriceRepository issuePriceRepository;

    @Transactional(readOnly = false)
    public List<SummaryCustomDto> getlist(int page, int size, String sort) {
        return genericRepository.findCustomList(page,size,sort);
    }

    public List<SummaryCustomDto> getcategorylist (int page, int size, String sort, String category) {
        return genericRepository.findCustomListCategory(page,size,sort,category);
    }

    @Transactional
    public SummaryResponseDto getreadylist(int page, int size, String category) {
        LocalDate localDate = LocalDate.now(); // 현재 날짜 가져오기
        Date sqlDate = Date.valueOf(localDate); // LocalDate를 java.sql.Date로 변환
            if(category.equals("all")) {
                int total =  issuePriceRepository.findissue(sqlDate);
                List<IssueCustomDto> dto = genericRepository.findCustomIssue(page,size,sqlDate);
                return new SummaryResponseDto(dto,page,size,total);
            }else {
                int total =  issuePriceRepository.findissuebyCategroy(sqlDate,category);
                List<IssueCustomDto> dto = genericRepository.findCustomIssueCategory(page, size, sqlDate, category);
                return new SummaryResponseDto(dto,page,size,total);
            }
    }

    @Transactional
    public SummaryResponseDto getstartlisty(int page, int size, String category) {
        LocalDate localDate = LocalDate.now(); // 현재 날짜 가져오기
        Date sqlDate = Date.valueOf(localDate); // LocalDate를 java.sql.Date로 변환
        if(category.equals("all")) {
            int total =  productRepository.findstart(sqlDate);
            List<IssueCustomDto> dto =  genericRepository.findCustomStart(page,size,sqlDate);
            return new SummaryResponseDto(dto,page,size,total);

        }else {
            int total =  productRepository.findstartCategory(sqlDate,category);
            List<IssueCustomDto> dto = genericRepository.findCustomStartCategory(page, size, sqlDate, category);
            return new SummaryResponseDto(dto,page,size,total);

        }
    }

    public SummaryResponseDto getfinish(int page, int size, String category) {
        if(category.equals("all")){
            int total = productRepository.findlistTotal("매각완료");
            List<finishCustomDto> dto =  genericRepository.findfinish(page,size);
            return new SummaryResponseDto(dto,page,size,total);
        }else {
            int total = productRepository.findlistTotalCategory("매각완료",category);
            List<finishCustomDto> dto = genericRepository.findfinishCategory(page,size,category);
            return new SummaryResponseDto(dto,page,size,total);
        }
    }

    public SummaryResponseDto getend(int page, int size , String category) {
        if(category.equals("all")){
            int total = productRepository.findlistTotal("공모완료");
            List<endCustomDto> dto = genericRepository.findend(page,size);
            return new SummaryResponseDto(dto,page,size,total);
        }else {
            int total = productRepository.findlistTotalCategory("공모완료",category);
            List<endCustomDto> dto = genericRepository.findendCategory(page, size, category);
            return new SummaryResponseDto(dto,page,size,total);
        }
    }

    public int getlistTotal(String status) {
        return productRepository.findlistTotal(status);
    }

    public int getlistTotalCategory(String status, String category) {
        return productRepository.findlistTotalCategory(status,category);

    }
}
