package com.moaguide.refactor.contents.service;

import com.moaguide.refactor.contents.repository.ContentRepository;
import com.moaguide.dto.NewDto.ContentDetailDto;
import com.moaguide.dto.NewDto.customDto.ContentBaseDto;
import com.moaguide.dto.NewDto.customDto.ContentInvestmentDto;
import com.moaguide.dto.NewDto.customDto.ContentPublishDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ContentService {
    private final ContentRepository contentRepository;

    @Transactional(readOnly = false)
    public ContentDetailDto findDetail(String productId, String nickname) {
        return contentRepository.findByDetail(productId,nickname);
    }

    @Transactional(readOnly = false)
    public ContentBaseDto findBase(String productId, String genre) {
        if(genre.equals("MOVIE") || genre.equals("EXHIBITION") || genre.equals("CULTURE")){
            ContentInvestmentDto investmentDto = contentRepository.findInvest(productId);
            ContentPublishDto publish = contentRepository.findPublish(productId);
            return  new ContentBaseDto(publish,investmentDto);
        }else{
            ContentPublishDto publish = contentRepository.findPublish(productId);
            return  new ContentBaseDto(publish);
        }
    }
}
