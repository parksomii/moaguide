package com.moaguide.domain.detail;


import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BuildingDetailRepository extends JpaRepository<BuildingDetail, Long> {

    @Procedure(name = "BuildingBaseProcedure")
    BuildingBaseDto findDetail(@Param("in_Product_Id") String productId);

}
