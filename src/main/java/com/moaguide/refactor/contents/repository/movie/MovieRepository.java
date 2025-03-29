package com.moaguide.refactor.contents.repository.movie;


import com.moaguide.refactor.contents.dto.MovieInfoDto;
import com.moaguide.refactor.contents.entity.movie.Movie;
import java.sql.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT new com.moaguide.refactor.contents.dto.MovieInfoDto(m.introduction,m.subGenre,m.releaseDate,m.rating,m.screeningTime,m.director,m.actor,m.distributor,m.original) FROM Movie m where m.productId.productId = :Id")
	MovieInfoDto findByProductId(@Param("Id") String productId);

	@Query("SELECT m.releaseDate FROM Movie m where m.productId.productId = :Id")
	Date findDate(@Param("Id") String productId);
}
