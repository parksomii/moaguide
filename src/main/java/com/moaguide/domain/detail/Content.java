package com.moaguide.domain.detail;

import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import java.util.Date;


@Entity
@Table(name = "content_Detail")
@AllArgsConstructor
@NoArgsConstructor
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Product productId;

    @Column(name = "genre")
    private String genre;

    @Column(name = "stock_Type")
    private String stockType;

    @Column(name = "min_Amount")
    private Long minAmount;

    @Column(name = "max_Amount")
    private Long maxAmount;

    @Column(name = "min_Investment")
    private Integer minInvestment;

    @Column(name = "Issue_Date")
    private Date issueDate;

    @Column(name = "expiration_Date")
    private Date expirationDate;

    @Column(name = "end_Date")
    private Date endDate;

    @Column(name = "total_Budget")
    private Long totalBudget;

    @Column(name = "customer_Unit_Price")
    private Integer customerUnitPrice;

    @Column(name = "profit_Loss_Ratio")
    private String profitLossRatio;

    @Column(name = "break_Even_Point")
    private String breakEvenPoint;

    @Column(name = "base_rate")
    private String baseRate;

    @Column(name = "max_rate")
    private String maxRate;

}
