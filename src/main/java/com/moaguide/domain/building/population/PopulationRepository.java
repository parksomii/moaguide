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

    @Procedure(name = "populate")
    List<PopulationDto> findByLastmonth(@Param("id") String ProductId, @Param("in_day") LocalDate lastmonth);
}
