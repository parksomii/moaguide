package com.moaguide.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SearchResponseDto {
    private List<searchCategoryDto> categoryDtos;
    private List<searchNewsDto> newsDtos;
}
