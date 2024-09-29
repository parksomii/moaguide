package com.moaguide.dto.NewDto.musicDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ViewDto {
    private String day;    // 조회날짜
    private String value;  // 조회수
}
