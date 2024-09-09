package com.moaguide.domain.detail;


import com.moaguide.dto.NewDto.BuildingDto.IdDto;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BuildingDetailRepository extends JpaRepository<BuildingDetail, Long> {

    @Transactional(readOnly = true)
    @Query(value = "CALL building_base(:in_Product_Id);", nativeQuery = true)
    BuildingBaseDto findDetail(@Param("in_Product_Id") String productId);
}
