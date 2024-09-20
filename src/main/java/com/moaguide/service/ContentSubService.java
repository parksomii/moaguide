package com.moaguide.service;


import com.moaguide.domain.content.BroadcastingRepository;
import com.moaguide.domain.content.ExhibitionRepository;
import com.moaguide.domain.content.PerformanceRepository;
import com.moaguide.domain.content.TravelRepository;
import com.moaguide.domain.content.movie.MovieScreenRepository;
import com.moaguide.domain.content.movie.MovieShowtimesRepository;
import com.moaguide.dto.NewDto.customDto.TravelInfoDto;
import com.moaguide.dto.NewDto.customDto.BroadcastInfoDto;
import com.moaguide.dto.NewDto.customDto.ExhibitInfoDto;
import com.moaguide.dto.NewDto.customDto.PerformanceInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContentSubService {
    private final ExhibitionRepository exhibitionRepository;
    private final BroadcastingRepository broadcastingRepository;
    private final PerformanceRepository performanceRepository;
    private final TravelRepository  travelRepository;
    private final MovieScreenRepository movieScreenRepository;
    private final MovieShowtimesRepository movieShowtimesRepository;

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

    public List<Integer> findScreen(String productId) {
        return null;
    }

    public List<Integer> findScreenten(String productId, Pageable pageable) {
        return movieScreenRepository.findByProductIdTop10(productId, pageable);
    }

    public List<Integer> findshowtime(String productId) {
        return movieShowtimesRepository.findByProductId(productId);
    }

    public List<Integer> findshowtimeten(String productId, Pageable pageable) {
        return movieShowtimesRepository.findByProductIdTop10(productId, pageable);
    }

}
