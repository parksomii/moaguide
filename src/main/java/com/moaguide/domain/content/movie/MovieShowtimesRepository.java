package com.moaguide.domain.content.movie;

import com.moaguide.dto.NewDto.customDto.MovieSubDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieShowtimesRepository extends JpaRepository<MovieShowtimes,Long> {


    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ms.showtimesCount,ms.day) FROM MovieShowtimes ms where ms.productId.productId =:Id order by ms.day")
    List<MovieSubDto> findByProductId(@Param("Id") String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(ms.showtimesCount,ms.day) FROM MovieShowtimes ms where ms.productId.productId =:Id order by ms.day")
    List<MovieSubDto> findByProductIdTop10(@Param("Id")String productId, Pageable pageable);
}
