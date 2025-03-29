package com.moaguide.refactor.notice.service;

import com.moaguide.refactor.notice.dto.AnnouncementDto;
import com.moaguide.refactor.notice.repository.AnnouncementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class AnnouncementService {

	private final AnnouncementRepository announcementRepository;

	public Page<AnnouncementDto> getNotice(Pageable pageable) {

		return announcementRepository.findAllNotice(pageable);
	}

	public AnnouncementDto getNoticeDetail(Long id) {
		return announcementRepository.findNoticeDetail(id);
	}
}
