package com.moaguide.domain.building.near;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.moaguide.dto.NewDto.BuildingDto.NearSubwayDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Repository
public interface NearSubwayRepository extends JpaRepository<NearSubway, Long> {

    @Procedure(name = "nearSub")
    List<NearSubwayDto> findBykeyword(@Param("id") String ProductId);
}
