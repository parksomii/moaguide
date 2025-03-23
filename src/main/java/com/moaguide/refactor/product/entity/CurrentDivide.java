package com.moaguide.refactor.product.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="Current_Divide") // 테이블명
public class CurrentDivide {

    @Id
    @Column(name="id")
    private Long divideId;

    @ManyToOne
    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    private Product productId;

    @Column(name="Payment_Day")
    private Date paymentDate;

    private double dividend;

    @Column(name="dividend_Rate")
    private double dividendRate;

    @Column(name="divide_cycle")
    private int divideCycle;

}
