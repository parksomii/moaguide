package com.moaguide.service.building;

import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.detail.BuildingDetailRepository;
import com.moaguide.dto.NewDto.BuildingDto.IdDto;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Slf4j
@Service
public class BuildingService {
    private final BuildingDetailRepository buildingRepository;

    public BuildingBaseDto findbase(String productId) {
        BuildingBaseDto building = buildingRepository.findDetail(productId);
        return building;
    }
}
