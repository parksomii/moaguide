package com.moaguide.domain.study;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubRoadmapRepository extends JpaRepository<SubRoadmap, Long> {

    @Query("SELECT s FROM SubRoadmap s where s.roadmapId.id = :id order by s.number")
    List<SubRoadmap> findAll(@Param("id") int category);
}
