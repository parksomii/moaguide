package com.moaguide.domain.notice;


import com.moaguide.dto.NewDto.customDto.NoticeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.NoticeDto(n.id,n.title,n.date) FROM Notice n where n.productId.productId=:prodcutId order by n.date desc")
    Page<NoticeDto> findByproductId(@Param("prodcutId") String productId, Pageable pageable);

    @Query("SELECT new com.moaguide.dto.NewDto.customDto.NoticeDto(n.title,n.date,n.content) FROM Notice n where n.id=:Id")
    NoticeDto findById(@Param("Id") long id);
}
