package com.moaguide.domain.building.population;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PopulationRepository extends JpaRepository<Population, Long> {


    @Query("SELECT p FROM Population p where p.districts.districtsId = :Id AND p.day = :day")
    List<Population> findByLastmonth(@Param("Id") int id, @Param("day") LocalDate lastmonth);
}
