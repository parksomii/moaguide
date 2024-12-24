package com.moaguide.domain.ArticleContent;

import com.moaguide.domain.CategoryContent.CategoryContent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ArticleContent")
public class ArticleContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private CategoryContent categoryId;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "image_link", columnDefinition = "TEXT")
    private String imageLink;

    @Column(name = "title", columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(name = "paywall_up", columnDefinition = "MEDIUMTEXT")
    private String paywallUp;

    @Column(name = "paywall_down", columnDefinition = "MEDIUMTEXT")
    private String paywallDown;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_premium", columnDefinition = "TINYINT(1)")
    private Boolean isPremium;

    @Column(name = "views")
    private Integer views;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "img_link", columnDefinition = "TEXT")
    private String imgLink;
}