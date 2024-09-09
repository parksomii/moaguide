package com.moaguide.domain.art;

import com.moaguide.dto.NewDto.customDto.ArtAuthorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArtAuthorRepository extends JpaRepository<ArtAuthor, Long> {
    @Transactional(readOnly = false)
    @Query(value = "CALL art_base(:in_Product_Id);", nativeQuery = true)
    ArtAuthorDto findAuthorDetail(@Param("in_Product_Id") String productId);
}
