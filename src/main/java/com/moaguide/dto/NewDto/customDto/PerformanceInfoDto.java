package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PerformanceInfoDto {
    private String venue;
    private String period;
    private String casting;
    private String director;
    private String showTimes;
    private String producer;
}
