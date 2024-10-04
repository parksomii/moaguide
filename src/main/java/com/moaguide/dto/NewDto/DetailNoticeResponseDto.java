package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.NoticeDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class DetailNoticeResponseDto {
    private List<NoticeDto> notice;
    private int total;
    private int page;
    private int size;

    public DetailNoticeResponseDto(Page<NoticeDto> noticeDtos) {
        this.notice = noticeDtos.getContent();
        this.page = noticeDtos.getNumber()+1;
        this.size = noticeDtos.getSize();
        this.total = noticeDtos.getTotalPages();
    }


}
