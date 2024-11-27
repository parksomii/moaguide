package com.moaguide.domain.ArticleContent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Getter;

@Entity
@Getter
@Table(name = "Content")
public class ArticleContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private int contentId;

    @Column(name = "category_id")
    private int categoryId;
    private String title;
    private String type;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_premium")
    private boolean isPremium;

    private int views;
    private int likes;
    private String description;
    private String img_link;
}
