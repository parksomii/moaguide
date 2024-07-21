package com.moaguide.domain.building.near;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NearBusRepository extends JpaRepository<NearBus, Long> {

    @Query("SELECT n FROM NearBus n where n.keyword = :keyword")
    NearBus findBykeyword(@Param("keyword") String keyword);
}
