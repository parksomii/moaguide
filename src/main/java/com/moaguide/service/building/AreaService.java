package com.moaguide.service.building;

import com.moaguide.domain.building.area.Area;
import com.moaguide.domain.building.area.AreaRepository;
import com.moaguide.dto.NewDto.AreaDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;

    @Transactional
    public List<AreaDto> findpolygon(String keyword ){
        List<AreaDto> area = areaRepository.findpolygon(keyword);
        return area;
    }
}
