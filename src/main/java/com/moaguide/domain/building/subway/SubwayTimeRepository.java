package com.moaguide.domain.building.subway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SubwayTimeRepository extends JpaRepository<SubwayTime, Long> {

    @Query("SELECT s FROM SubwayTime s where s.keyword = :keyward AND s.Year = :year AND s.Month = :month")
    SubwayTime findByLastmonth(@Param("keyward") String keyword,@Param("year") int year,@Param("month")int month);
}
