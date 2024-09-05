package com.moaguide.domain.transaction;


import com.moaguide.domain.summary.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    private Product productId;

    @Column(name = "trade_Day",nullable = false)
    private LocalDate tradeDay;

    @Column(nullable = false)
    private long price;

    private double price_rate;

    public String getProductId() {
        return productId.getProductId();
    }

    public String getName() {
        return productId.getName();
    }

    public int getPiece(){
        return productId.getPiece();
    }

    public Integer getViews() {
        return productId.getViews();
    }

    public String getCategory() { return productId.getPlatformId().getCategory();}

    public Integer getnowPiece() {
        return productId.getNowPiece();
    }

    public String getPlatform() {
        return productId.getPlatformId().getPlatform();
    }
}