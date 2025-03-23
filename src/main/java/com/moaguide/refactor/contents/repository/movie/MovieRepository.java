package com.moaguide.refactor.contents.repository.movie;


import com.moaguide.dto.NewDto.customDto.MovieInfoDto;
import com.moaguide.refactor.contents.entity.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.MovieInfoDto(m.introduction,m.subGenre,m.releaseDate,m.rating,m.screeningTime,m.director,m.actor,m.distributor,m.original) FROM Movie m where m.productId.productId = :Id")
    MovieInfoDto findByProductId(@Param("Id") String productId);

    @Query("SELECT m.releaseDate FROM Movie m where m.productId.productId = :Id")
    Date findDate(@Param("Id")String productId);
}
