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
    @Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.RentDto(r.year,r.quarter,r.region,r.rent) FROM Rent r,BuildingDetail bd where r.keyword = bd.keyword  AND r.type = :type AND bd.productId.productId = :productId AND r.year between :syear and :eyear order by r.region,r.year,r.quarter")
    List<RentDto> findBytype(@Param("productId") String product_Id, @Param("type") String type,@Param("syear") int syear, @Param("eyear")int eyear);

    @Query("SELECT distinct new com.moaguide.dto.NewDto.BuildingDto.TypeDto( r.type) FROM BuildingDetail bd,Rent r where bd.keyword = r.keyword and bd.productId.productId=:productId")
    List<TypeDto> findType(@Param("productId")String product_Id);

    @Query("SELECT COUNT(DISTINCT r.region) FROM Rent r,BuildingDetail bd where r.keyword = bd.keyword and bd.productId.productId = :productId")
    int findDistinctRegionCount(@Param("productId") String product_Id);
}
