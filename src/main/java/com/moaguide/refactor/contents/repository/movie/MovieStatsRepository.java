package com.moaguide.refactor.contents.repository.movie;

import com.moaguide.refactor.contents.dto.MovieStatsDto;
import com.moaguide.refactor.contents.entity.movie.MovieStats;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStatsRepository extends JpaRepository<MovieStats, Long> {

	@Query("select new com.moaguide.refactor.contents.dto.MovieStatsDto(m.day,m.region,m.screenCount,m.totalRevenue,m.revenueShare,m.totalAudience,m.audienceShare,mo.releaseDate) FROM MovieStats m,Movie mo where m.productId.productId = mo.productId.productId and m.productId.productId =:Id")
	List<MovieStatsDto> findByProductId(@Param("Id") String productId);
}
