package com.moaguide.domain.building.location;

import com.moaguide.dto.LocationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT new com.moaguide.dto.LocationDto( p.name ,l.longitude,l.latitude,bd.location) " +
            "FROM Product p,Location l,BuildingDetail bd " +
            "where p.productId = l.productId.productId " +
            "AND p.productId = bd.productId " +
            "and p.productId = :id ")
    LocationDto findByProductId(@Param("id") String productId);
}
