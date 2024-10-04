package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.ContentBaseDto;
import lombok.Getter;

@Getter
public class ContentResponseDto {
    private ContentBaseDto base;
    private Object object;

    // 하나의 생성자로 통합
    public ContentResponseDto(ContentBaseDto base, Object object) {
        this.base = base;
        this.object = object;
    }

    // object가 없을 때를 위한 생성자
    public ContentResponseDto(ContentBaseDto base) {
        this.base = base;
        this.object = null;
    }
}
