package com.moaguide.refactor.product.service;

import com.moaguide.refactor.product.repository.DivideRepository;
import com.moaguide.refactor.product.dto.DivideCustomDto;
import com.moaguide.refactor.product.dto.DivideGraphDto;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DivideService {

	private final DivideRepository divideRepository;
	private final EntityManager entityManager;

	// 지급주기별 저작권료 & 시가저작권료 조회
	public DivideGraphDto getDivideData(String productId, int month) {
		List<DivideCustomDto> divideList = new ArrayList<>();
		double max_rate = 0.0;
		double min_rate = 0.0;
		double max_value = 0.0;
		double min_value = 0.0;

		if (month <= 12 || month == 100) {
			Date date = Date.valueOf(LocalDate.now().minusMonths(month));
			String sql = "SELECT d.Payment_Day,d.dividend,d.dividend_Rate FROM Divide d where d.Payment_Day >= ?1 and d.product_Id = ?2 order by Payment_Day";

			List<Object[]> results = entityManager.createNativeQuery(sql)
				.setParameter(1, date)
				.setParameter(2, productId)
				.getResultList();

			if (results.size() > 0) {
				max_rate = ((BigDecimal) results.get(0)[2]).doubleValue();
				min_rate = ((BigDecimal) results.get(0)[2]).doubleValue();
				max_value = ((BigDecimal) results.get(0)[1]).doubleValue();
				min_value = ((BigDecimal) results.get(0)[1]).doubleValue();
				// DTO로 매핑
				for (Object[] result : results) {
					double currentRate = ((BigDecimal) results.get(0)[2]).doubleValue();
					double currentValue = ((BigDecimal) results.get(0)[1]).doubleValue();
					DivideCustomDto dto = new DivideCustomDto(
						(Date) result[0],        // Payment_Day
						currentValue, // dividend
						currentRate  // dividend_Rate
					);

					if (min_rate > currentRate) {
						min_rate = currentRate;
					}
					if (max_rate < currentRate) {
						max_rate = currentRate;
					}
					if (min_value > currentValue) {
						min_value = currentValue;
					}
					if (max_value < currentValue) {
						max_value = currentValue;
					}

					divideList.add(dto);
				}
			}
		}
		return new DivideGraphDto(divideList, max_value, min_value, max_rate, min_rate);
	}

}
