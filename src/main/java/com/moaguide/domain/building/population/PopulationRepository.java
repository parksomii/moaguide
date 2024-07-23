package com.moaguide.domain.building.population;

import com.moaguide.dto.NewDto.PopulationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PopulationRepository extends JpaRepository<Population, Long> {


    @Query("SELECT new com.moaguide.dto.NewDto.PopulationDto(p.weekDay, p.total, p.age0, p.age10, p.age20, p.age30, p.age40, p.age50, p.age60, p.age70, p.man, p.girl) FROM Population p where p.districts.districtsId = :Id AND p.day = :day")
    List<PopulationDto> findByLastmonth(@Param("Id") int id, @Param("day") LocalDate lastmonth);
}
