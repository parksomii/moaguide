package com.moaguide.domain.content.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long> {

    @Procedure(name = "getMoviesInDateRange")
    List<MovieSchedule> findByschedule(@Param("movieId") String productId);

}
