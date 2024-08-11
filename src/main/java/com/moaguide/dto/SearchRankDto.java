package com.moaguide.dto;

import lombok.Data;

@Data
public class SearchRankDto {
    private String keyword;
    private int rank;

    public SearchRankDto(String keyword, int rank) {
        this.keyword = keyword;
        this.rank = rank;
    }
}
