package com.moaguide.domain.study;

import com.moaguide.dto.NewDto.customDto.SubRoadmapDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubRoadmapRepository extends JpaRepository<SubRoadmap, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.SubRoadmapDto(s.id, s.number, s.title, s.description) FROM sub_Roadmap s WHERE s.roadmapId.id = :id ORDER BY s.number")
    List<SubRoadmapDto> findAllDto(@Param("id") int category);

}
