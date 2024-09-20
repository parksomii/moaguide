package com.moaguide.dto.NewDto.musicDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ViewDto {
    private String viewCount;  // 조회수
    private String viewDate;    // 조회날짜
}
