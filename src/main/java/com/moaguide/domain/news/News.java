package com.moaguide.domain.news;

import com.moaguide.dto.NewsDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    private String title;

    private String link;

    private Date date;

    private String description;

    private int views;

    private String category;

    public News toDto(){
        return new News(id, keyword, title, link, date, description, views,category);
    }
}
