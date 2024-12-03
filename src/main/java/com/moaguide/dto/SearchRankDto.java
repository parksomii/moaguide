package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

@Data
@NoArgsConstructor    // 기본 생성자 추가
@AllArgsConstructor
@Profile({"blue","green"})
public class SearchRankDto {
    private String keyword;
    private int rank;
}
