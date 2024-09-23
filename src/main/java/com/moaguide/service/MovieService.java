package com.moaguide.service;


import com.moaguide.domain.content.MovieRepository;
import com.moaguide.domain.content.movie.MovieSchedule;
import com.moaguide.domain.content.movie.MovieScheduleRepository;
import com.moaguide.domain.content.movie.MovieStatsRepository;
import com.moaguide.dto.NewDto.customDto.MovieInfoDto;
import com.moaguide.dto.NewDto.customDto.MovieStatsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieStatsRepository movieStatsRepository;
    private final MovieScheduleRepository movieScheduleRepository;

    public MovieInfoDto findmovie(String productId) {
        return movieRepository.findByProductId(productId);
    }

    @Transactional
    public List<MovieSchedule> findSechedule(String productId) {
        return  movieScheduleRepository.findByschedule(productId);
    }

    public List<MovieStatsDto> findStats(String productId) {
        return movieStatsRepository.findByProductId(productId);
    }

}
