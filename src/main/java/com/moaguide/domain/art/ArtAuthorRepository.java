package com.moaguide.domain.art;

import com.moaguide.dto.NewDto.customDto.ArtAuthorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ArtAuthorRepository extends JpaRepository<ArtAuthor, Long> {
}
