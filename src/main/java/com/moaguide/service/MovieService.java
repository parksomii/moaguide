package com.moaguide.service;


import com.moaguide.domain.content.MovieRepository;
import com.moaguide.dto.NewDto.customDto.MovieInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieInfoDto findmovie(String productId) {
        return movieRepository.findByProductId(productId);
    }
}
