package com.moaguide.domain.building.vacancyrate;

import com.moaguide.domain.building.rent.Rent;
import com.moaguide.dto.NewDto.BuildingDto.VacancyrateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRateRepository extends JpaRepository<VacancyRate, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.VacancyrateDto(v.year,v.quarter,v.region,v.vacancyRate) FROM VacancyRate v where v.keyword = :keyward AND v.type = :type")
    List<VacancyrateDto> findByLastmonth(@Param("keyward") String keyword, @Param("type") String type);
}
