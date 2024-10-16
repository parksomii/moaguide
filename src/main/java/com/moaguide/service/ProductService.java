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
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
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
    public List<SummaryCustomDto> getlist(int page, int size, String sort,String nickname) {
        return genericRepository.findCustomList(page,size,sort,nickname);
    }

    @Transactional
    public List<SummaryCustomDto> getcategorylist (int page, int size, String sort, String category,String nickname) {
        return genericRepository.findCustomListCategory(page,size,sort,category,nickname);
    }

    @Transactional
    public SummaryResponseDto getreadylist(int page, int size, String category,String nickname) {
        LocalDate localDate = LocalDate.now(); // 현재 날짜 가져오기
        Date sqlDate = Date.valueOf(localDate); // LocalDate를 java.sql.Date로 변환
            if(category.equals("all")) {
                int total =  issuePriceRepository.findissue(sqlDate);
                List<IssueCustomDto> dto = genericRepository.findCustomIssue(page,size,sqlDate,nickname);
                return new SummaryResponseDto(dto,page,size,total);
            }else if (category.equals("bookmark")){
                int total =  issuePriceRepository.findissuebyBookcark(sqlDate,nickname);
                List<IssueCustomDto> dto = genericRepository.findCustomIssuebyBookmark(page,size,sqlDate,nickname);
                return new SummaryResponseDto(dto,page,size,total);
            } else {
//                int total =  issuePriceRepository.findissuebyCategroy(sqlDate,category);
                int total =  genericRepository.findissuebyCategroy(sqlDate,category);
                List<IssueCustomDto> dto = genericRepository.findCustomIssueCategory(page, size, sqlDate, category,nickname);
                return new SummaryResponseDto(dto,page,size,total);
            }
    }

    @Transactional
    public SummaryResponseDto getstartlisty(int page, int size, String category,String nickname) {
        LocalDate localDate = LocalDate.now(); // 현재 날짜 가져오기
        Date sqlDate = Date.valueOf(localDate); // LocalDate를 java.sql.Date로 변환
        if(category.equals("all")) {
            int total =  genericRepository.getStartCount(sqlDate);
            List<IssueCustomDto> dto =  genericRepository.findCustomStart(page,size,sqlDate,nickname);
            return new SummaryResponseDto(dto,page,size,total);
        }else if(category.equals("bookmark")){
            int total = genericRepository.getStartCountBookmark(sqlDate,nickname);
            List<IssueCustomDto> dto = genericRepository.findCustomStartBookmark(page, size, sqlDate, nickname);
            return new SummaryResponseDto(dto,page,size,total);
        }else {
            int total =  genericRepository.getStartCountCategory(sqlDate,category);
            List<IssueCustomDto> dto = genericRepository.findCustomStartCategory(page, size, sqlDate, category,nickname);
            return new SummaryResponseDto(dto,page,size,total);

        }
    }

    @Transactional
    public SummaryResponseDto getfinish(int page, int size, String category, String nickname) {
        if(category.equals("all")){
            int total = productRepository.findlistTotal("매각완료");
            List<finishCustomDto> dto =  genericRepository.findfinish(page,size,nickname);
            return new SummaryResponseDto(dto,page,size,total);
        }else if(category.equals("bookmark")){
            int total = productRepository.findlistTotalByBookmark("매각완료",nickname);
            List<endCustomDto> dto = genericRepository.findfinishBookmark(page,size,nickname);
            return new SummaryResponseDto(dto,page,size,total);
        } else {
            int total = productRepository.findlistTotalCategory("매각완료",category);
            List<finishCustomDto> dto = genericRepository.findfinishCategory(page,size,category,nickname);
            return new SummaryResponseDto(dto,page,size,total);
        }
    }

    @Transactional
    public SummaryResponseDto getend(int page, int size , String category,String nickname) {
        if(category.equals("all")){
            int total = productRepository.findlistTotal("공모완료");
            List<endCustomDto> dto = genericRepository.findend(page,size,nickname);
            return new SummaryResponseDto(dto,page,size,total);
        }else if(category.equals("bookmark")){
            int total = productRepository.findlistTotalByBookmark("공모완료",nickname);
            List<endCustomDto> dto = genericRepository.findendBookmark(page,size,nickname);
            return new SummaryResponseDto(dto,page,size,total);
        } else {
            int total = productRepository.findlistTotalCategory("공모완료",category);
            List<endCustomDto> dto = genericRepository.findendCategory(page, size, category,nickname);
            return new SummaryResponseDto(dto,page,size,total);
        }
    }

    public int getlistTotal(String status){
        return productRepository.findlistTotal(status);
    }

    public int getlistTotalCategory(String status, String category) {
        return productRepository.findlistTotalCategory(status,category);

    }

    public List<SummaryCustomDto> getlistBookmark(int page, int size, String sort,String nickname) {
        return genericRepository.findlistBookmark(page,size,sort,nickname);
    }

    public int getlistTotalByBookmark(String status,String nickname) {
        return productRepository.findlistTotalByBookmark(status,nickname);
    }

    public List<SummaryCustomDto> home(String category,String nickname) {
        switch (category){
            case "building":
                return genericRepository.findCustomListCategory(0,3,"lastDivide_rate desc","building",nickname);
            case "music":
                return genericRepository.findCustomListCategory(0,3,"lastDivide_rate desc","music",nickname);
            case "content":
                return genericRepository.findHometCategory("content",nickname);
            case "art":
                return genericRepository.findHometCategory("art",nickname);
            case "cow":
                return genericRepository.findHometCategory("cow",nickname);
            default:
                return null;
        }

    }

    @Transactional
    public void viewupdate(String productId) {
        productRepository.updateByProductId(productId);
    }
}
