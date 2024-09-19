package com.moaguide.service;


import com.moaguide.domain.content.BroadcastingRepository;
import com.moaguide.domain.content.ExhibitionRepository;
import com.moaguide.domain.content.PerformanceRepository;
import com.moaguide.domain.content.TravelRepository;
import com.moaguide.dto.NewDto.customDto.TravelInfoDto;
import com.moaguide.dto.NewDto.customDto.BroadcastInfoDto;
import com.moaguide.dto.NewDto.customDto.ExhibitInfoDto;
import com.moaguide.dto.NewDto.customDto.PerformanceInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContentSubService {
    private final ExhibitionRepository exhibitionRepository;
    private final BroadcastingRepository broadcastingRepository;
    private final PerformanceRepository performanceRepository;
    private final TravelRepository  travelRepository;

    public ExhibitInfoDto findexhibit(String productId) {
        return exhibitionRepository.findByProductId(productId);
    }

    public BroadcastInfoDto findbroadcast(String productId) {
        return broadcastingRepository.findByProductId(productId);
    }

    public PerformanceInfoDto findperformance(String productId) {
        return performanceRepository.findByProductId(productId);
    }

    public TravelInfoDto findtravel(String productId) {
        return travelRepository.findByProductId(productId);

    }
}
