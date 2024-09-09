package com.moaguide.domain.building.location;

import com.moaguide.dto.LocationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT new com.moaguide.dto.LocationDto( p.name ,l.longitude,l.latitude) " +
            "FROM Product p,Location l " +
            "where p.productId = l.productId.productId " +
            "and p.productId = :id ")
    LocationDto findByProductId(@Param("id") String productId);
}
