package com.moaguide.domain.building.subway;

import com.moaguide.dto.NewDto.BuildingDto.SubwayDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubwayDayRepository extends JpaRepository<SubwayDay, Long> {

    @Procedure(name = "SubwayDay")
    List<SubwayDto> callSubwayDayProcedure(@Param("productId") String productId);
}
