package com.moaguide.domain.study;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String imageLink;

    private Date date;

    private String content;

    private String pdfLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_Roadmap_Id", foreignKey = @ForeignKey(name = "Sub_Roadmap_article_FK"))
    private SubRoadmap subRoadmapId;

}