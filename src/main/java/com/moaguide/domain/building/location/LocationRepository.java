package com.moaguide.domain.building.location;

import com.moaguide.dto.LocationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Procedure(name = "locate")
    LocationDto findByProductId(@Param("id") String productId);
}
