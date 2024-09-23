package com.moaguide.domain.content.movie;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoviePeopleRepository extends JpaRepository<MoviePeople, Long> {

    @Query("SELECT m FROM MoviePeople m where m.name = :keyword order by m.officialPeople desc")
    List<MoviePeople> findByname(@Param("keyword") String keyword, Pageable pageable);
}
