package com.moaguide.domain.study;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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

    private Timestamp date;

    private String link;
    private String category;
}