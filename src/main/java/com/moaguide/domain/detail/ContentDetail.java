package com.moaguide.domain.detail;

import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "content_Detail")
public class ContentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_Id", nullable = false)
    private Product product;

    @Column(name = "genre", length = 40)
    private String genre;

    @Column(name = "stock_Type", length = 40)
    private String stockType;

    @Column(name = "min_Amount")
    private Long minAmount;

    @Column(name = "max_Amount")
    private Long maxAmount;

    @Column(name = "min_Investment")
    private Integer minInvestment;

    @Column(name = "Issue_Date")
    private LocalDate issueDate;

    @Column(name = "expiration_Date")
    private LocalDate expirationDate;

    @Column(name = "end_Date")
    private LocalDate endDate;

    @Column(name = "total_Budget")
    private Long totalBudget;

    @Column(name = "customer_Unit_Price")
    private Integer customerUnitPrice;

    @Column(name = "profit_Loss_Ratio", length = 30)
    private String profitLossRatio;

    @Column(name = "break_Even_Point", length = 60)
    private String breakEvenPoint;

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;

    @Column(name = "sub_Genre", length = 40)
    private String subGenre;

    @Column(name = "release_Date")
    private LocalDate releaseDate;

    @Column(name = "rating", length = 30)
    private String rating;

    @Column(name = "screening_Time")
    private Integer screeningTime;

    @Column(name = "Director", length = 20)
    private String director;

    @Column(name = "actor", length = 200)
    private String actor;

    @Column(name = "distributor", length = 30)
    private String distributor;

    @Column(name = "original", length = 40)
    private String original;
}
