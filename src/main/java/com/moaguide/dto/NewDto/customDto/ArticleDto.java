package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ArticleDto {

    private Long id;

    private String title;

    private String description;

    private String imageLink;

}
