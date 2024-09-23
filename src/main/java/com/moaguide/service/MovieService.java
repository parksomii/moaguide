package com.moaguide.service;


import com.moaguide.domain.GenericRepository;
import com.moaguide.domain.content.MovieRepository;
import com.moaguide.domain.content.movie.MovieStats;
import com.moaguide.domain.content.movie.MovieStatsRepository;
import com.moaguide.dto.NewDto.customDto.MovieInfoDto;
import com.moaguide.dto.NewDto.customDto.MovieScheduleDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieStatsRepository movieStatsRepository;

    public MovieInfoDto findmovie(String productId) {
        return movieRepository.findByProductId(productId);
    }

    @Transactional
    public List<MovieScheduleDto> findSechedule(String productId) {
        return movieRepository.findByschedule(productId);
    }

    public List<MovieStats> findStats(String productId) {
        return movieStatsRepository.findByProductId(productId);
    }
}
