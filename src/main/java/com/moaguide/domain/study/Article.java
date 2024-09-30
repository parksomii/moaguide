package com.moaguide.domain.study;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Entity(name="article")
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name="image_link")
    private String imageLink;

    private Date date;

    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Roadmap_Id", foreignKey = @ForeignKey(name = "Roadmap_article_FK"))
    private Roadmap RoadmapId;

    private String link;
    private String category;
}