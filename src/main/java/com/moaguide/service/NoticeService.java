package com.moaguide.service;

import com.moaguide.refactor.notice.repository.NoticeRepository;
import com.moaguide.dto.NewDto.customDto.NoticeDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public Page<NoticeDto> findbyProductId(String productId, Pageable pageable) {
        Page<NoticeDto> noticeDtos = noticeRepository.findByproductId(productId,pageable);
        return noticeDtos;
    }

    public NoticeDto findBydetail(long noticeId) {
        NoticeDto noticeDto = noticeRepository.findById(noticeId);
        return noticeDto;
    }
}
