package com.moaguide.refactor.contents.repository.movie;


import com.moaguide.refactor.contents.dto.MovieSubDto;
import com.moaguide.refactor.contents.entity.movie.MovieDetail;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDetailRepository extends JpaRepository<MovieDetail, Long> {

	@Query("select new com.moaguide.refactor.contents.dto.MovieSubDto(ms.screenCount,ms.day)  FROM MovieDetail ms, Movie m where ms.productId.productId = m.productId.productId and ms.productId.productId =:Id and ms.day>=m.releaseDate order by ms.day")
	List<MovieSubDto> findByScreen(@Param("Id") String productId);

	@Query("select new com.moaguide.refactor.contents.dto.MovieSubDto(ms.screenCount,ms.day)  FROM MovieDetail ms, Movie m where ms.productId.productId = m.productId.productId and ms.productId.productId =:Id and ms.day>=m.releaseDate order by ms.day")
	List<MovieSubDto> findByScreenTop10(@Param("Id") String productId, Pageable pageable);

	@Query("select new com.moaguide.refactor.contents.dto.MovieSubDto(ms.showtimesCount,ms.day) FROM MovieDetail ms, Movie m where ms.productId.productId = m.productId.productId and ms.productId.productId =:Id and ms.day>=m.releaseDate order by ms.day")
	List<MovieSubDto> findByshowtime(@Param("Id") String productId);

	@Query("select new com.moaguide.refactor.contents.dto.MovieSubDto(ms.showtimesCount,ms.day) FROM MovieDetail ms, Movie m where ms.productId.productId = m.productId.productId and ms.productId.productId =:Id and ms.day>=m.releaseDate order by ms.day")
	List<MovieSubDto> findByshowtimeTop10(@Param("Id") String productId, Pageable pageable);

	@Query("select new com.moaguide.refactor.contents.dto.MovieSubDto(ms.audienceCount,ms.day) FROM MovieDetail ms, Movie m where ms.productId.productId = m.productId.productId and ms.productId.productId =:Id and ms.day>=m.releaseDate order by ms.day")
	List<MovieSubDto> findByaudience(@Param("Id") String productId);

	@Query("select new com.moaguide.refactor.contents.dto.MovieSubDto(ms.audienceCount,ms.day)  FROM MovieDetail ms, Movie m where ms.productId.productId = m.productId.productId and ms.productId.productId =:Id and ms.day>=m.releaseDate order by ms.day")
	List<MovieSubDto> findByaudienceTop10(@Param("Id") String productId, Pageable pageable);

	@Query("select new com.moaguide.refactor.contents.dto.MovieSubDto(ms.ranking,ms.day) FROM MovieDetail ms, Movie m where ms.productId.productId = m.productId.productId and ms.productId.productId =:Id and ms.day>=m.releaseDate order by ms.day")
	List<MovieSubDto> findByrank(@Param("Id") String productId);

	@Query("select new com.moaguide.refactor.contents.dto.MovieSubDto(ms.ranking,ms.day) FROM MovieDetail ms, Movie m where ms.productId.productId = m.productId.productId and ms.productId.productId =:Id and ms.day>=m.releaseDate order by ms.day")
	List<MovieSubDto> findByrankTop10(@Param("Id") String productId, Pageable pageable);

	@Query("select new com.moaguide.refactor.contents.dto.MovieSubDto(ms.revenue,ms.day) FROM MovieDetail ms, Movie m where ms.productId.productId = m.productId.productId and ms.productId.productId =:Id and ms.day>=m.releaseDate order by ms.day")
	List<MovieSubDto> findByrevenue(@Param("Id") String productId);

	@Query("select new com.moaguide.refactor.contents.dto.MovieSubDto(ms.revenue,ms.day) FROM MovieDetail ms, Movie m where ms.productId.productId = m.productId.productId and ms.productId.productId =:Id and ms.day>=m.releaseDate order by ms.day")
	List<MovieSubDto> findByrevenueTop10(@Param("Id") String productId, Pageable pageable);
}