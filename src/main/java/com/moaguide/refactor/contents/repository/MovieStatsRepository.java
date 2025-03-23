package com.moaguide.refactor.contents.repository;

import com.moaguide.dto.NewDto.customDto.MovieStatsDto;
import com.moaguide.refactor.contents.entity.MovieStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieStatsRepository extends JpaRepository<MovieStats, Long> {

    @Query("select new com.moaguide.dto.NewDto.customDto.MovieStatsDto(m.day,m.region,m.screenCount,m.totalRevenue,m.revenueShare,m.totalAudience,m.audienceShare,mo.releaseDate) FROM MovieStats m,Movie mo where m.productId.productId = mo.productId.productId and m.productId.productId =:Id")
    List<MovieStatsDto> findByProductId(@Param("Id") String productId);
}
