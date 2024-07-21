package com.moaguide.service.building;

import com.moaguide.domain.detail.BuildingDetailRepository;
import com.moaguide.dto.BuildingDetailDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Slf4j
@Service
public class BuildingService {
    private final BuildingDetailRepository buildingRepository;


    public BuildingDetailDto detail(String id) {
        BuildingDetailDto buildingDetailDto = buildingRepository.findByproductId(id).toDTO();
        return buildingDetailDto;
    }

}
