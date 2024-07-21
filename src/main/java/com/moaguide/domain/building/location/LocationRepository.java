package com.moaguide.domain.building.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select l FROM Location l where l.productId.productId = :productId")
    Location findByProductId(@Param("productId") String productId);
}
