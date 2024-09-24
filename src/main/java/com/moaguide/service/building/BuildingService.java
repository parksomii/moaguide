package com.moaguide.service.building;

import com.moaguide.domain.detail.BuildingDetailRepository;
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


    @Transactional(readOnly = false)
    public BuildingReponseDto findBydetail(String productId, String nickname) {
        BuildingReponseDto building = buildingRepository.findBuildingDetail(productId,nickname);
        return building;
    }

}
