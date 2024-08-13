package com.moaguide.domain.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT count(b) from Bookmark b where b.userId.customNumber=:customNumber")
    int countByUser(@Param("customNumber") Long customNumber);
}