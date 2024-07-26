package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DetailTransactionResponseDto {
    private List<TransactionDto> transaction;
}
