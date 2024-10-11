package com.moaguide.domain.content.movie;


import com.moaguide.dto.NewDto.customDto.MovieSubDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieDetailRepository {

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ms.screenCount,ms.day)  FROM MovieDetail ms where ms.productId.productId =:Id order by ms.day")
    List<MovieSubDto> findByScreen(String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ms.screenCount,ms.day)  FROM MovieDetail ms where ms.productId.productId =:Id order by ms.day")
    List<MovieSubDto> findByScreenTop10(String productId, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ms.showtimesCount,ms.day) FROM MovieDetail ms where ms.productId.productId =:Id order by ms.day")
    List<MovieSubDto> findByshowtime(String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ms.showtimesCount,ms.day) FROM MovieDetail ms where ms.productId.productId =:Id order by ms.day")
    List<MovieSubDto> findByshowtimeTop10(String productId, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ma.audienceCount,ma.day) FROM MovieDetail ma where ma.productId.productId =:Id order by ma.day")
    List<MovieSubDto> findByaudience(String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ma.audienceCount,ma.day)  FROM MovieDetail ma where ma.productId.productId =:Id order by ma.day")
    List<MovieSubDto> findByaudienceTop10(String productId, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(mr.ranking,mr.day) FROM MovieDetail mr where mr.productId.productId =:Id order by mr.day")
    List<MovieSubDto> findByrank(String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(mr.ranking,mr.day) FROM MovieDetail mr where mr.productId.productId =:Id order by mr.day")
    List<MovieSubDto> findByrankTop10(String productId, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(mr.revenue,mr.day) FROM MovieDetail mr where mr.productId.productId =:Id order by mr.day")
    List<MovieSubDto> findByrevenue(String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(mr.revenue,mr.day) FROM MovieDetail mr where mr.productId.productId =:Id order by mr.day")
    List<MovieSubDto> findByrevenueTop10(String productId, Pageable pageable);
}