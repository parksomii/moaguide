package com.moaguide.refactor.contents.repository.movie;

import com.moaguide.refactor.contents.entity.movie.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieScheduleRepository extends JpaRepository<MovieSchedule, Long> {

    @Procedure(name = "GetMoviesIntenDateRange")
    List<MovieSchedule> findByschedule(@Param("movieId") String productId);

}
