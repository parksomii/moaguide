package com.moaguide.refactor.building.repository.near;

import com.moaguide.refactor.building.dto.NearBusDto;
import com.moaguide.refactor.building.entity.near.NearBus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NearBusRepository extends JpaRepository<NearBus, Long> {

	@Query("SELECT new com.moaguide.dto.NewDto.BuildingDto.NearBusDto(n.node)" +
		"FROM BuildingDetail bd ,NearBus n " +
		"where bd.keyword = n.keyword " +
		"and bd.productId.productId = :keyword")
	List<NearBusDto> findBykeyword(@Param("keyword") String keyword);
}
