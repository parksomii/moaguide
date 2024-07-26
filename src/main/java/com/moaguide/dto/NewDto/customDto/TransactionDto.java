package com.moaguide.dto.NewDto.customDto;


import com.moaguide.domain.summary.Summary;
import com.moaguide.domain.transaction.Transaction;
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
    private LocalTime time;
    private long price;
}
