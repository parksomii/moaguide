package com.moaguide.domain.content.movie;

import com.moaguide.dto.NewDto.customDto.MovieSubDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRevenueRepository extends JpaRepository<MovieRevenue, Long> {

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(mr.revenue,mr.day) FROM MovieRevenue mr where mr.productId.productId =:Id order by mr.day")
    List<MovieSubDto> findByProductId(@Param("Id") String productId);

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieSubDto(mr.revenue,mr.day) FROM MovieRevenue mr where mr.productId.productId =:Id order by mr.day")
    List<MovieSubDto> findByProductIdTop10(@Param("Id") String productId, Pageable pageable);
}
