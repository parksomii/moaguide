package com.moaguide.domain.building.subway;

import com.moaguide.dto.NewDto.BuildingDto.SubwayDto;
import com.moaguide.dto.NewDto.BuildingDto.SubwayWeekDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubwayMonthRepository extends JpaRepository<SubwayMonth, Long> {


    @Procedure( name ="GetSubwayMonth", outputParameterName = "subwayMapping")
    List<SubwayDto> findByProductId(@Param("productId") String productId);
}
