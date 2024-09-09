package com.moaguide.domain.detail;

import com.moaguide.dto.NewDto.customDto.HanwooFarmDto;
import com.moaguide.dto.NewDto.customDto.HanwooPublishDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface HanwooDetailRepository extends JpaRepository<HanwooDetail, Long> {
    @Transactional(readOnly = false)
    @Query(value = "CALL hanwoo_base(:in_Product_Id);", nativeQuery = true)
    HanwooPublishDto findHanwooDetail(@Param("in_Product_Id") String productId);

    @Transactional(readOnly = false)
    @Query(value = "CALL hanwoo_base(:in_Product_Id);", nativeQuery = true)
    HanwooFarmDto findfarmDetail(@Param("in_Product_Id") String productId);
}
