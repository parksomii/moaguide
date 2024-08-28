package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class searchCategoryDto {
    private String productId;
    private String name;
    private String platform;
    private String category;
}
