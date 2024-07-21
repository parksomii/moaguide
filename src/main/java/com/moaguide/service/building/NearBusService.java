package com.moaguide.service.building;

import com.moaguide.domain.building.near.NearBus;
import com.moaguide.domain.building.near.NearBusRepository;
import com.moaguide.domain.building.near.NearSubway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class NearBusService {
    private final NearBusRepository nearBusRepository;

    public NearBus findBykeyword(String keyword) {
        NearBus nearBus = nearBusRepository.findBykeyword(keyword);
        log.info("keyword:{} nearBus:{}", keyword, nearBus);
        return nearBus;
    }
}
