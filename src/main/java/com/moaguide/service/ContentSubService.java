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
    private final MovieDetailRepository movieDetailRepository;

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
        return movieDetailRepository.findByScreen(productId);
    }

    public List<MovieSubDto> findScreenten(String productId, Pageable pageable) {
        return movieDetailRepository.findByScreenTop10(productId, pageable);
    }

    public List<MovieSubDto> findshowtime(String productId) {
        return movieDetailRepository.findByshowtime(productId);
    }

    public List<MovieSubDto> findshowtimeten(String productId, Pageable pageable) {
        return movieDetailRepository.findByshowtimeTop10(productId, pageable);
    }

    public List<MovieSubDto> findaudience(String productId) {
        return  movieDetailRepository.findByaudience(productId);
    }

    public List<MovieSubDto> findaudienceten(String productId, Pageable pageable) {
        return movieDetailRepository.findByaudienceTop10(productId,pageable);
    }

    public List<MovieSubDto> findrank(String productId) {
        return  movieDetailRepository.findByrank(productId);
    }

    public List<MovieSubDto> findrankten(String productId, Pageable pageable) {
        return movieDetailRepository.findByrankTop10(productId,pageable);
    }

    public List<MovieSubDto> findrevenue(String productId) {
        return  movieDetailRepository.findByrevenue(productId);
    }

    public List<MovieSubDto> findrevenueten(String productId, Pageable pageable) {
        return movieDetailRepository.findByrevenueTop10(productId,pageable);
    }
}
