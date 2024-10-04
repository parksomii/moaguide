package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class searchProductDto {
    private String productId;
    private String name;
    private String platform;
    private String category;
}
