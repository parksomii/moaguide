package com.moaguide.service.building;

import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.detail.BuildingDetailRepository;
import com.moaguide.dto.NewDto.BuildingDto.DistricIdDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Slf4j
@Service
public class BuildingService {
    private final BuildingDetailRepository buildingRepository;

    public BuildingDetail detail(String id) {
        BuildingDetail buildingDetail = buildingRepository.findByproductId(id);
        return buildingDetail;
    }

    public DistricIdDto detailByDistricId(String productId) {
        DistricIdDto buildingDetail = buildingRepository.findDistricId(productId);
        return buildingDetail;
    }
}
