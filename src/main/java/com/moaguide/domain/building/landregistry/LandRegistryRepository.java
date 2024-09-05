package com.moaguide.domain.building.landregistry;


import com.moaguide.domain.summary.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LandRegistryRepository extends JpaRepository<LandRegistry, Product> {

    @Query("SELECT L FROM LandRegistry L WHERE L.productId.productId = :id")
    LandRegistry findByProductId(@Param("id") String id);
}
