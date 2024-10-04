package com.moaguide.dto.NewDto.customDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class ArticleDto {

    private Long id;

    private String title;

    private String description;

    private String imageLink;

    private Timestamp date;

    private String link;
}
