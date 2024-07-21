package com.moaguide.domain.transaction;


import com.moaguide.domain.summary.Summary;
import com.moaguide.dto.TransactionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private Summary productId;

    @Column(name = "trade_Day",nullable = false)
    private LocalDate tradeDay;

    @Column(name = "trade_Time")
    private LocalTime tradeTime;

    @Column(nullable = false)
    private long price;

    public String getProductId() {
        return productId.getProductId();
    }

    public String getName() {
        return productId.getName();
    }

    public int getPiece(){
        return productId.getPiece();
    }

    public TransactionDto toDto(){
        return new TransactionDto(id, productId, tradeDay, tradeTime, price);
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