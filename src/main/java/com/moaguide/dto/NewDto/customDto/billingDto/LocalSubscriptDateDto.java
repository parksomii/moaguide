package com.moaguide.dto.NewDto.customDto.billingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Profile("local")
@AllArgsConstructor
@Getter
public class LocalSubscriptDateDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime paymentDate;
}
