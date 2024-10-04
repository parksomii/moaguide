package com.moaguide.dto.NewDto.musicDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchDto {
    private String value;  // 검색량
    private String day;   // 검색날짜
}
