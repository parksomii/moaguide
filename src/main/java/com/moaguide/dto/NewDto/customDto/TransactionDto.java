package com.moaguide.dto.NewDto.customDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private LocalDate date;
    private long price;
}
