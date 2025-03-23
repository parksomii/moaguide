package com.moaguide.refactor.building.repository;


import com.moaguide.domain.product.Product;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import com.moaguide.refactor.building.entity.LandRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LandRegistryRepository extends JpaRepository<LandRegistry, Product> {

    @Query("SELECT L FROM LandRegistry L WHERE L.productId.productId = :id")
    LandRegistry findByProductId(@Param("id") String id);

    @Procedure(name = "BuildingBaseProcedure")
    BuildingBaseDto findDetail(@Param("in_Product_Id") String productId);

}
