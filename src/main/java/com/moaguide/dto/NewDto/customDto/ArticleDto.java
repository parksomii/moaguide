package com.moaguide.dto.NewDto.customDto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class ArticleDto {

    private Long id;

    private String title;

    private String description;

    private String imageLink;

    private Date date;

    private String link;
}
