package com.moaguide.service;


import com.moaguide.domain.content.BroadcastingRepository;
import com.moaguide.domain.content.ExhibitionRepository;
import com.moaguide.domain.content.PerformanceRepository;
import com.moaguide.domain.content.TravelRepository;
import com.moaguide.domain.content.movie.*;
import com.moaguide.dto.NewDto.customDto.*;
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
    private final MovieAudienceRepository movieAudienceRepository;
    private final MovieRankRepository movieRankRepository;
    private final MovieRevenueRepository movieRevenueRepository;

    public ExhibitInfoDto findexhibit(String productId) {
        return exhibitionRepository.findByProductId(productId);
    }

    public BroadcastInfoDto findbroadcast(String productId) {
        return broadcastingRepository.findByProductId(productId);
    }

    public PerformanceInfoDto findperformance(String productId) {
        return performanceRepository.findByProductId(productId);
    }

    public List<MovieSubDto> findScreen(String productId) {
        return movieScreenRepository.findByProductId(productId);
    }

    public List<MovieSubDto> findScreenten(String productId, Pageable pageable) {
        return movieScreenRepository.findByProductIdTop10(productId, pageable);
    }

    public List<MovieSubDto> findshowtime(String productId) {
        return movieShowtimesRepository.findByProductId(productId);
    }

    public List<MovieSubDto> findshowtimeten(String productId, Pageable pageable) {
        return movieShowtimesRepository.findByProductIdTop10(productId, pageable);
    }

    public List<MovieSubDto> findaudience(String productId) {
        return  movieAudienceRepository.findByProductId(productId);
    }

    public List<MovieSubDto> findaudienceten(String productId, Pageable pageable) {
        return movieAudienceRepository.findByProductIdTop10(productId,pageable);
    }

    public List<MovieSubDto> findrank(String productId) {
        return  movieRankRepository.findByProductId(productId);
    }

    public List<MovieSubDto> findrankten(String productId, Pageable pageable) {
        return movieRankRepository.findByProductIdTop10(productId,pageable);
    }

    public List<MovieSubDto> findrevenue(String productId) {
        return  movieRevenueRepository.findByProductId(productId);
    }

    public List<MovieSubDto> findrevenueten(String productId, Pageable pageable) {
        return movieRevenueRepository.findByProductIdTop10(productId,pageable);
    }
}
