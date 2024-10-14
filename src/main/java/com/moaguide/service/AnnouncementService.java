package com.moaguide.service;

import com.moaguide.domain.Announcement.AnnouncementRepository;
import com.moaguide.dto.NewDto.customDto.AnnouncementDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public List<AnnouncementDto> getNotice() {
        return announcementRepository.findAllNotice();
    }

    public AnnouncementDto getNoticeDetail(Long id) {
        return announcementRepository.findNoticeDetail(id);
    }
}
