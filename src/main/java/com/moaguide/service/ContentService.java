package com.moaguide.service;

import com.moaguide.domain.GenericRepository;
import com.moaguide.domain.detail.Content;
import com.moaguide.domain.detail.ContentRepository;
import com.moaguide.dto.NewDto.ContentDetailDto;
import com.moaguide.dto.NewDto.customDto.ContentBaseDto;
import com.moaguide.dto.NewDto.customDto.ContentInvestmentDto;
import com.moaguide.dto.NewDto.customDto.ContentPublishDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ContentService {
    private final ContentRepository contentRepository;

    @Transactional(readOnly = false)
    public ContentDetailDto findDetail(String productId) {
        log.info("받은 상품명: {}", productId);
        return contentRepository.findByDetail(productId);
    }

    @Transactional(readOnly = true)
    public ContentBaseDto findBase(String productId, String genre) {
        log.info("받은 상품명: {}", productId);
        if(genre.equals("MOVIE") || genre.equals("EXHIBITION") || genre.equals("CULTURE") || genre.equals("TRAVEL") ){
            ContentInvestmentDto investmentDto = contentRepository.findInvest(productId);
            ContentPublishDto publish = contentRepository.findPublish(productId);
            return  new ContentBaseDto(publish,investmentDto);
        }else{
            ContentPublishDto publish = contentRepository.findPublish(productId);
            return  new ContentBaseDto(publish);
        }
    }
}
