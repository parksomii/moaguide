package com.moaguide.dto.NewDto.customDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleSummaryDto {
    private Long id;

    private String title;

    private String description;

    private String imageLink;

    private String category;
}
