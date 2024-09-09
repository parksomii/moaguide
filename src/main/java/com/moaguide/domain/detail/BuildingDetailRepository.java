package com.moaguide.domain.detail;


import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import com.moaguide.dto.NewDto.customDto.BuildingReponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingDetailRepository extends JpaRepository<BuildingDetail, Long> {

    @Procedure(name = "BuildingBaseProcedure")
    BuildingBaseDto findDetail(@Param("in_Product_Id") String productId);

    @Procedure(name = "building_detail")
    BuildingReponseDto Detail(@Param("in_Product_Id") String Product_Id);

}
