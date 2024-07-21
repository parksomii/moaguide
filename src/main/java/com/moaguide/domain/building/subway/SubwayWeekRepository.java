package com.moaguide.domain.building.subway;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubwayWeekRepository extends JpaRepository<SubwayWeek, Long> {

    @Query("SELECT s FROM SubwayWeek s where s.keyword = :keyward AND s.Year = :year AND s.Month = :month")
    List<SubwayWeek> findByLastmonth(@Param("keyward") String keyword, @Param("year") int year, @Param("month")int month);
}
