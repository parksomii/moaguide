package com.moaguide.domain.building.near;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.moaguide.dto.NearSubwayDto;

import java.util.List;



@Repository
public interface NearSubwayRepository extends JpaRepository<NearSubway, Long> {

    @Query("SELECT s.route,s.station,s.distance,s.time from NearSubway s where s.keyword = :keyword ")
    List<NearSubwayDto> findBykeyword(@Param("keyword") String keyword);
}
