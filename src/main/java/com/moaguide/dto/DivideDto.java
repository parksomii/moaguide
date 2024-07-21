package com.moaguide.dto;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.summary.Summary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DivideDto {
    private int divideId;
    private Summary productId;
    private Date decisionDay;
    private Date paymentDate;
    private double dividend;
    private double dividendRate;

    public Divide toEntity() { return new Divide(divideId, productId, decisionDay, paymentDate, dividend, dividendRate); }
}
