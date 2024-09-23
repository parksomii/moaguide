package com.moaguide.domain.content;


import com.moaguide.dto.NewDto.customDto.MovieInfoDto;
import com.moaguide.dto.NewDto.customDto.MovieScheduleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.MovieInfoDto(m.introduction,m.subGenre,m.releaseDate,m.rating,m.screeningTime,m.director,m.actor,m.distributor,m.original) FROM Movie m where m.productId.productId = :Id")
    MovieInfoDto findByProductId(@Param("Id") String productId);

    @Procedure(procedureName = "GetMoviesInDateRange")
    List<MovieScheduleDto> findByschedule(@Param("movieId") String productId);
}
