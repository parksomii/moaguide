package com.moaguide.service;


import com.moaguide.domain.content.MovieRepository;
import com.moaguide.domain.content.movie.MovieScheduleRepository;
import com.moaguide.domain.content.movie.MovieStats;
import com.moaguide.domain.content.movie.MovieStatsRepository;
import com.moaguide.dto.NewDto.customDto.MovieInfoDto;
import com.moaguide.dto.NewDto.customDto.MovieScheduleDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
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
    public List<MovieScheduleDto> findSechedule(String productId) {
//        Date date = movieRepository.findDate(productId);
//        LocalDate localDate = date.toLocalDate();
//        Date before = Date.valueOf(localDate.minusDays(6));
//        Date after = Date.valueOf(localDate.plusDays(7));
        return  movieScheduleRepository.findByschedule(productId);
    }

    public List<MovieStats> findStats(String productId) {
        return movieStatsRepository.findByProductId(productId);
    }

}
