package com.moaguide.domain.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MusicDetailRepository extends JpaRepository<MusicDetail, Long> {

    @Query("SELECT m FROM MusicDetail m WHERE m.productId.productId = :productId")
    MusicDetail findByproductId(@Param("productId") String id);
}
