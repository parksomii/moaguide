package com.moaguide.domain.building.near;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NearSubwayRepository extends JpaRepository<NearSubway, Long> {

    List<NearSubway> findBykeyword(String keyword);
}
