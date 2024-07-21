package com.moaguide.service.building;

import com.moaguide.domain.building.area.Area;
import com.moaguide.domain.building.area.AreaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;

    @Transactional
    public List<Area> findpolygon(String name ){
        List<Area> area = areaRepository.findpolygon(name);
        return area;
    }
}
