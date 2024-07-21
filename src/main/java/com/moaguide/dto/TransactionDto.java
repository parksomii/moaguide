package com.moaguide.dto;


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
    private Long id;
    private Summary productId;
    private LocalDate tradeDay;
    private LocalTime tradeTime;
    private Long price;


    public Transaction toEntity(){
        return new Transaction(id,productId,tradeDay,tradeTime,price);
    }
}
