package com.moaguide.domain.hanwoo;

import com.moaguide.dto.NewDto.customDto.AveragePriceDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AveragePriceRepository extends JpaRepository<AveragePrice, Long> {
//    List<AveragePriceDto> findAveragePrice(String category, int year);
//    List<AveragePriceDto> findAverageCattlePrice(String category, int year);
}
