package com.moaguide.service.building;

import com.moaguide.domain.building.near.NearSubway;
import com.moaguide.domain.building.near.NearSubwayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NearSubwayService {
    private final NearSubwayRepository nearSubwayRepository;

    public List<NearSubway> findBykeyword(String keyword) {
        List<NearSubway> nearSubway = nearSubwayRepository.findBykeyword(keyword);
        return nearSubway;
    }
}
