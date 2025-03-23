package com.moaguide.refactor.contents.repository.movie;

import com.moaguide.refactor.contents.entity.movie.MoviePeople;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoviePeopleRepository extends JpaRepository<MoviePeople, Long> {

    @Query("SELECT m FROM MoviePeople m where m.name = :keyword order by m.officialPeople desc")
    List<MoviePeople> findByname(@Param("keyword") String keyword, Pageable pageable);
}
