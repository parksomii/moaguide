package com.moaguide.domain.divide;

import com.moaguide.domain.summary.Summary;
import com.moaguide.dto.DivideDto;
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
    private Summary productId;

    @Column(name="Decision_Day")
    private Date decisionDay;

    @Column(name="Payment_Day")
    private Date paymentDate;

    private double dividend;

    @Column(name="dividend_Rate")
    private double dividendRate;

    public DivideDto toDto(){
        return new DivideDto(divideId, productId, decisionDay, paymentDate, dividend, dividendRate);
    }

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

    public Divide (Summary productId,Date decisionDay,double dividend,double dividendRate){
        this.productId = productId;
        this.decisionDay = decisionDay;
        this.dividend = dividend;
        this.dividendRate = dividendRate;
    }
}
