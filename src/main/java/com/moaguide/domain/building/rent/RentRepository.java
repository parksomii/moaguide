package com.moaguide.domain.building.rent;


import com.moaguide.dto.NewDto.BuildingDto.RentDto;
import com.moaguide.dto.NewDto.BuildingDto.TypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.RentDto(r.year,r.quarter,r.region,r.rent) FROM Rent r where r.keyword = :keyword AND r.type = :type")
    List<RentDto> findBytype(@Param("keyword") String keyword, @Param("type") String type);

    @Query("SELECT distinct new com.moaguide.dto.NewDto.BuildingDto.TypeDto(r.type) FROM Rent r WHERE r.keyword = :keyword")
    List<TypeDto> findType(@Param("keyword")String keyword);
}
