package com.moaguide.refactor.building.repository;


import com.moaguide.refactor.building.dto.AreaDto;
import com.moaguide.refactor.building.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

	@Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.AreaDto(A.areaSize,A.areaName,A.polygon,A.latitude,A.longitude,A.color) FROM Area A, BuildingDetail bd WHERE bd.keyword = A.keyword and bd.productId.productId = :product_Id  ")
	List<AreaDto> findpolygon(@Param("product_Id") String product_Id);
}
