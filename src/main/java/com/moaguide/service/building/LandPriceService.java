package com.moaguide.service.building;

import com.moaguide.domain.building.lnadprice.LandPriceRepository;
import com.moaguide.dto.NewDto.BuildingDto.LandDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LandPriceService {
    private LandPriceRepository landPriceRepository;

    public List<LandDto> priceList(String id) {
        List<LandDto> list = landPriceRepository.findAllByproductId(id);
        return list;
    }
}
