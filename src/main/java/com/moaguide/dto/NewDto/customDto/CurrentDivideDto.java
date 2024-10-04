package com.moaguide.dto.NewDto.customDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.parameters.P;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class CurrentDivideDto {
    private Double lastDivide;
    private Integer divideCycle;
    private Date paymentDay;
    private Double divideRate;

}
