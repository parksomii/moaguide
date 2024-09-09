package com.moaguide.service.hanwoo;

import com.moaguide.domain.detail.HanwooDetailRepository;
import com.moaguide.dto.NewDto.customDto.HanwooFarmDto;
import com.moaguide.dto.NewDto.customDto.HanwooPublishDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class HanwooService {
    private final HanwooDetailRepository hanwooRepository;

    public HanwooPublishDto findHanwoo(String productId) {
        HanwooPublishDto hanwoo = hanwooRepository.findHanwooDetail(productId);
        return hanwoo;
    }

    public HanwooFarmDto findFarm(String productId) {
        HanwooFarmDto farm = hanwooRepository.findfarmDetail(productId);
        return farm;
    }
}
