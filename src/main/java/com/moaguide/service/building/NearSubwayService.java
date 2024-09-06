package com.moaguide.service.building;

import com.moaguide.domain.building.near.NearSubwayRepository;
import com.moaguide.dto.NewDto.BuildingDto.NearSubwayDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NearSubwayService {
    private final NearSubwayRepository nearSubwayRepository;

    public List<NearSubwayDto> findBykeyword(String productId) {
        List<NearSubwayDto> nearSubway = nearSubwayRepository.findBykeyword(productId);
        return nearSubway;
    }
}
