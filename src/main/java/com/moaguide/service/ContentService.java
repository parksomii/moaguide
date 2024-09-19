package com.moaguide.service;

import com.moaguide.domain.detail.Content;
import com.moaguide.domain.detail.ContentRepository;
import com.moaguide.dto.NewDto.ContentDetailDto;
import com.moaguide.dto.NewDto.customDto.ContentBaseDto;
import com.moaguide.dto.NewDto.customDto.ContentInvestmentDto;
import com.moaguide.dto.NewDto.customDto.ContentPublishDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;


    public ContentDetailDto findDetail(String productId) {
        return contentRepository.findByDetail(productId);
    }

    public ContentBaseDto findBase(String productId, String genre) {
        if(genre.equals("MOVIE") || genre.equals("EXHIBITION") || genre.equals("CULTURE") || genre.equals("TRAVEL") ){
            ContentPublishDto publish = contentRepository.findpublish(productId);
            ContentInvestmentDto investmentDto = contentRepository.findInvest(productId);
            return  new ContentBaseDto(publish,investmentDto);
        }else{
            ContentPublishDto publish = contentRepository.findpublish(productId);
            return  new ContentBaseDto(publish);
        }
    }
}
