package com.moaguide.domain.building.businessarea;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessAreaRepository extends JpaRepository<BusinessArea, Long> {

    @Query("SELECT b FROM BusinessArea b WHERE b.productId.productId = :id")
    BusinessArea findByproductId(@Param("id") String id);
}
