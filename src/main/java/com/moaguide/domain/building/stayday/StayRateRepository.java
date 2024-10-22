package com.moaguide.domain.building.stayday;

import com.moaguide.dto.NewDto.BuildingDto.StayDayDto;
import com.moaguide.dto.NewDto.BuildingDto.StayRateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StayRateRepository extends JpaRepository<StayRate, Integer> {

    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.StayRateDto(s.day,s.rate,s.value) FROM StayRate s where s.keyword =:keyword and YEAR(s.day) between :startyear and :endyear")
    List<StayRateDto> findByKeywordandyear(String keyword, int syear, int eyear);
}
