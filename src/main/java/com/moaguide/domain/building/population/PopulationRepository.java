package com.moaguide.domain.building.population;

import com.moaguide.dto.NewDto.BuildingDto.PopulationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface PopulationRepository extends JpaRepository<Population, Long> {

    @Query("select new com.moaguide.dto.NewDto.BuildingDto.PopulationDto(p.weekDay ,p.total ,p.age0 ,p.age10 ,p.age20,p.age30,p.age40,p.age50,p.age60,p.age70,p.man,p.girl )" +
            "FROM Population p,BuildingDetail b " +
            "where p.districts.districtsId = b.districtsId.districtsId and p.day = :in_day and b.productId.productId = :id "+
            "ORDER BY CASE " +
            "WHEN p.weekDay = 'Monday' THEN 1 " +
            "WHEN p.weekDay = 'Tuesday' THEN 2 " +
            "WHEN p.weekDay = 'Wednesday' THEN 3 " +
            "WHEN p.weekDay = 'Thursday' THEN 4 " +
            "WHEN p.weekDay = 'Friday' THEN 5 " +
            "WHEN p.weekDay = 'Saturday' THEN 6 " +
            "WHEN p.weekDay = 'Sunday' THEN 7 " +
            "END")
    List<PopulationDto> findByLastmonth(@Param("id") String ProductId, @Param("in_day") LocalDate lastmonth);
}
