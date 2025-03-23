package com.moaguide.refactor.contents.entity;


import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "Movie_Stats")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "Product_Id", referencedColumnName = "Product_Id", nullable = false)
    private Product productId;

    private Date day;
    private String region;
    @Column(name = "screen_count")
    private int screenCount;
    @Column(name = "total_revenue")
    private Long totalRevenue;
    @Column(name = "revenue_share")
    private Double revenueShare;
    @Column(name = "total_audience")
    private Long totalAudience;
    @Column(name = "audience_share")
    private Double audienceShare;
}
