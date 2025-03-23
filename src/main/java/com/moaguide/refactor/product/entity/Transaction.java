package com.moaguide.refactor.product.entity;


import com.moaguide.dto.NewDto.customDto.endCustomDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SqlResultSetMapping(
        name = "endCustomDtoMapping",
        classes = @ConstructorResult(
                targetClass = endCustomDto.class,
                columns = {
                        @ColumnResult(name = "productId", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "category", type = String.class),
                        @ColumnResult(name = "platform", type = String.class),
                        @ColumnResult(name = "totalPrice", type = Long.class),
                        @ColumnResult(name = "bookmark", type = Boolean.class),
                        @ColumnResult(name = "sale", type = Boolean.class)
                }
        )
)
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