package com.moaguide.domain.content.movie;

import com.moaguide.dto.NewDto.customDto.MovieScheduleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long> {

    @Procedure(procedureName = "GetMoviesInDateRange")
    List<MovieSchedule> findByschedule(@Param("movieId") String productId);

}
