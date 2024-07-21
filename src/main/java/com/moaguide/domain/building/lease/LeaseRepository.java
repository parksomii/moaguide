package com.moaguide.domain.building.lease;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaseRepository extends JpaRepository<Lease, Long> {

    @Query("SELECT L FROM Lease L WHERE L.productId.productId = :productId")
    List<Lease> findByproductId(@Param("productId") String id);
}
