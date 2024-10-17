package com.moaguide.domain.Announcement;

import com.moaguide.dto.NewDto.customDto.AnnouncementDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnnouncementRepository  extends JpaRepository<Announcement, Long> {
    // 공지 리스트 조회
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.AnnouncementDto(a.id, a.title, a.date, a.content) FROM Announcement a order by a.date desc")
    Page<AnnouncementDto> findAllNotice(Pageable pageable);

    // 공지 상세 조회
    @Query("SELECT new com.moaguide.dto.NewDto.customDto.AnnouncementDto(a.id, a.title, a.date, a.content) FROM Announcement a WHERE a.id = :id")
    AnnouncementDto findNoticeDetail(Long id);
}
