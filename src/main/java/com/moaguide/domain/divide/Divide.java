package com.moaguide.domain.divide;

import com.moaguide.domain.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name="DIVIDE")
public class Divide {
    @Id
    @Column(name="id")
    private int divideId;

    @ManyToOne
    @JoinColumn(referencedColumnName="product_Id",name="product_Id")
    private Product productId;

    @Column(name="Decision_Day")
    private Date decisionDay;

    @Column(name="Payment_Day")
    private Date paymentDate;

    private double dividend;

    @Column(name="dividend_Rate")
    private double dividendRate;

    public String getProductId() {
        return productId.getProductId();
    }

    public String getCategory() {
        return productId.getPlatformId().getCategory();
    }

    public String getName() {
        return productId.getName();
    }

    public double getDividend() {
        return dividend;
    }

    public double getDividendRate() {
        return dividendRate;
    }

    public Divide (Product productId, Date decisionDay, double dividend, double dividendRate){
        this.productId = productId;
        this.decisionDay = decisionDay;
        this.dividend = dividend;
        this.dividendRate = dividendRate;
    }

    public String getPlatform() {
        return productId.getPlatformId().getPlatform();
    }
}
