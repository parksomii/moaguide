package com.moaguide.refactor.payments.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Profile;

@Profile("local")
@AllArgsConstructor
@Getter
public class LocalSubscriptDateDto {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private LocalDateTime paymentDate;
}
