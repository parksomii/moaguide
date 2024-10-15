package com.moaguide.domain.building.subway;

import com.moaguide.dto.NewDto.BuildingDto.SubwayDto;
import com.moaguide.dto.NewDto.BuildingDto.SubwayTimeDto;
import io.netty.util.Mapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubwayDayRepository extends JpaRepository<SubwayDay, Long> {

    @Query(value = "CALL GetSubwayDay(:productId)", nativeQuery = true)
    List<SubwayDto> callSubwayDayProcedure(@Param("productId") String productId);
}
