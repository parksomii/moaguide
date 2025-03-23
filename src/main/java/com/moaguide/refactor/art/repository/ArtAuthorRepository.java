package com.moaguide.refactor.art.repository;

import com.moaguide.refactor.art.entity.ArtAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtAuthorRepository extends JpaRepository<ArtAuthor, Long> {

}
