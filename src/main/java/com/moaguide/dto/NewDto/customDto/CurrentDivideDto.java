package com.moaguide.dto.NewDto.customDto;


import lombok.Getter;
import org.springframework.security.core.parameters.P;

import java.sql.Date;

@Getter
public class CurrentDivideDto {
    private Double lastDivide;
    private Integer divideCycle;
    private Date paymentDay;
    private Double divideRate;

    public CurrentDivideDto(BuildingBaseDto buildingBaseDto) {
        this.lastDivide = buildingBaseDto.getLastDivide();
        this.divideCycle = buildingBaseDto.getDivideCycle();
        this.paymentDay = buildingBaseDto.getPaymentDay();
        this.divideRate = buildingBaseDto.getDivideRate();
    }

}
