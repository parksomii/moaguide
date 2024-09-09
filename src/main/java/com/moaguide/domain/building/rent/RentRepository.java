package com.moaguide.domain.building.rent;


import com.moaguide.dto.NewDto.BuildingDto.RentDto;
import com.moaguide.dto.NewDto.BuildingDto.TypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.RentDto(r.year,r.quarter,r.region,r.rent) FROM Rent r where r.keyword = :keyword AND r.type = :type")
    List<RentDto> findBytype(@Param("keyword") String keyword, @Param("type") String type);

    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.TypeDto(r.type) FROM BuildingDetail bd,Rent r where bd.keyword = r.keyword and bd.productId.productId=:productId")
    List<TypeDto> findType(@Param("productId")String product_Id);
}
