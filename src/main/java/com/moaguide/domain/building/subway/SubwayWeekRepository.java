package com.moaguide.domain.building.subway;

import com.moaguide.dto.NewDto.BuildingDto.SubwayWeekDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubwayWeekRepository extends JpaRepository<SubwayWeek, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.SubwayWeekDto(s.Year,s.Month,s.weekDay,s.totalAlighting,s.totalBoarding) FROM SubwayWeek s where s.keyword = :keyward AND s.Year = :year AND s.Month = :month")
    List<SubwayWeekDto> findByLastmonth(@Param("keyward") String keyword, @Param("year") int year, @Param("month")int month);
}
