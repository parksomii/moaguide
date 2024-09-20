package com.moaguide.domain.content.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieStatsRepository extends JpaRepository<MovieStats, Long> {

    @Query("select m FROM MovieStats m where m.productId.productId =:Id")
    List<MovieStats> findByProductId(@Param("Id") String productId);
}
