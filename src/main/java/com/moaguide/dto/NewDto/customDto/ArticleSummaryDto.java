package com.moaguide.dto.NewDto.customDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleSummaryDto {
    private String title;

    private String description;

    private String imageLink;

    private Date date;

    private String link;
}
