package com.moaguide.service.building;

import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.detail.BuildingDetailRepository;
import com.moaguide.dto.NewDto.BuildingDto.IdDto;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import com.moaguide.dto.NewDto.customDto.BuildingReponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Slf4j
@Service
public class BuildingService {
    private final BuildingDetailRepository buildingRepository;


    @Transactional
    public BuildingReponseDto findBydetail(String productId) {
        BuildingReponseDto building = buildingRepository.Detail(productId);
        return building;
    }

    @Transactional(readOnly = false)
    public BuildingBaseDto findbase(String productId) {
        BuildingBaseDto building = buildingRepository.findDetail(productId);
        return building;
    }
}
