package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor    // 기본 생성자 추가
@AllArgsConstructor
public class SearchRankDto {
    private String keyword;
    private int rank;

}
