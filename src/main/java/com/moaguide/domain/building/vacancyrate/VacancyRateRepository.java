package com.moaguide.domain.building.vacancyrate;

import com.moaguide.dto.NewDto.BuildingDto.VacancyrateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRateRepository extends JpaRepository<VacancyRate, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.VacancyrateDto(v.year,v.quarter,v.region,v.vacancyRate) FROM VacancyRate v,BuildingDetail bd where v.keyword = bd.keyword AND v.type = :type AND bd.productId.productId = :product_Id AND v.year between :syear and :eyear")
    List<VacancyrateDto> findByLastmonth(@Param("product_Id") String product_Id, @Param("type") String type,@Param("syear") int syear, @Param("eyear")int eyear);
}
