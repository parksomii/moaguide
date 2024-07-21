package com.moaguide.service.building;

import com.moaguide.domain.building.lnadprice.LandPrice;
import com.moaguide.domain.building.lnadprice.LandPriceRepository;
import com.moaguide.dto.LandPriceDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class LandPriceService {
    private LandPriceRepository landPriceRepository;

    public List<LandPriceDto> priceList(String id) {
        List<LandPrice> list = landPriceRepository.findAllByproductId(id);
        List<LandPriceDto> dtoList = new ArrayList<>();
        for (LandPrice landPrice : list) {
            dtoList.add(landPrice.toDto());
        }
        return dtoList;
    }
}
