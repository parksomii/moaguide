package com.moaguide.domain.content.movie;


import com.moaguide.dto.NewDto.customDto.MovieSubDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieDetailRepository  extends JpaRepository<MovieDetail, Long> {

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ms.screenCount,ms.day)  FROM MovieDetail ms where ms.productId.productId =:Id order by ms.day")
    List<MovieSubDto> findByScreen(@Param("Id") String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ms.screenCount,ms.day)  FROM MovieDetail ms where ms.productId.productId =:Id order by ms.day")
    List<MovieSubDto> findByScreenTop10(@Param("Id") String productId, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ms.showtimesCount,ms.day) FROM MovieDetail ms where ms.productId.productId =:Id order by ms.day")
    List<MovieSubDto> findByshowtime(@Param("Id") String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ms.showtimesCount,ms.day) FROM MovieDetail ms where ms.productId.productId =:Id order by ms.day")
    List<MovieSubDto> findByshowtimeTop10(@Param("Id") String productId, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ma.audienceCount,ma.day) FROM MovieDetail ma where ma.productId.productId =:Id order by ma.day")
    List<MovieSubDto> findByaudience(@Param("Id") String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ma.audienceCount,ma.day)  FROM MovieDetail ma where ma.productId.productId =:Id order by ma.day")
    List<MovieSubDto> findByaudienceTop10(@Param("Id") String productId, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(mr.ranking,mr.day) FROM MovieDetail mr where mr.productId.productId =:Id order by mr.day")
    List<MovieSubDto> findByrank(@Param("Id") String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(mr.ranking,mr.day) FROM MovieDetail mr where mr.productId.productId =:Id order by mr.day")
    List<MovieSubDto> findByrankTop10(@Param("Id") String productId, Pageable pageable);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(mr.revenue,mr.day) FROM MovieDetail mr where mr.productId.productId =:Id order by mr.day")
    List<MovieSubDto> findByrevenue(@Param("Id") String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(mr.revenue,mr.day) FROM MovieDetail mr where mr.productId.productId =:Id order by mr.day")
    List<MovieSubDto> findByrevenueTop10(@Param("Id") String productId, Pageable pageable);
}