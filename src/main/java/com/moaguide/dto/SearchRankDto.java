package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRankDto {
    private String keyword;
    private int rank;

}
