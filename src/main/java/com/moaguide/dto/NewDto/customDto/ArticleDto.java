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

    private String content;

    private String pdfLink;


    public ArticleDto(Long id, String title, String description, String imageLink) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
    }

    public ArticleDto(String title, Date date, String imageLink, String content, String pdfLink) {
        this.title = title;
        this.date = date;
        this.imageLink = imageLink;
        this.content = content;
        this.pdfLink = pdfLink;
    }
}
