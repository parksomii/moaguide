package com.moaguide.dto;

import lombok.*;

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
