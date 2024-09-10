package com.moaguide.domain.detail;

import com.moaguide.dto.NewDto.HanwooBaseResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface HanwooDetailRepository extends JpaRepository<HanwooDetail, Long> {
    @Query(value = "CALL hanwoo_base(:productId)", nativeQuery = true)
    HanwooBaseResponseDto findHanwooDetail(@Param("productId") String productId);
}
