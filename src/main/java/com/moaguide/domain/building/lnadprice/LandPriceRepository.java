package com.moaguide.domain.building.lnadprice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandPriceRepository extends JpaRepository<LandPrice, Long> {

    @Query("SELECT L from LandPrice L WHERE L.productId.productId = :productId ORDER BY L.baseYear DESC, L.baseDay DESC")
    List<LandPrice> findAllByproductId(@Param("productId") String Id);
}
