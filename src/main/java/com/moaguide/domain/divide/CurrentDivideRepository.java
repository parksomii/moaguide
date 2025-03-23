package com.moaguide.domain.divide;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentDivideRepository extends JpaRepository<CurrentDivide, Long> {

    @Query("SELECT cd.divideCycle FROM CurrentDivide cd where cd.productId.productId = :product_Id")
    Integer findCycle(@Param("product_Id") String productId);

}

