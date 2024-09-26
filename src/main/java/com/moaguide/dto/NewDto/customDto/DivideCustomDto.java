package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DivideCustomDto {
    //최근 배당금
    private String paymentDate;
    private double divide;
    private double divide_rate;

    public DivideCustomDto(Date paymentDate, double divide, double divide_rate) {
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.paymentDate = targetFormat.format(paymentDate);
        this.divide = divide;
        this.divide_rate = divide_rate;
    }
}
