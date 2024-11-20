package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryDto {
    private int categoryId;
    private String name;
    private String description;

}
