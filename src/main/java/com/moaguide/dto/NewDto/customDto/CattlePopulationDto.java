package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CattlePopulationDto {
    private Date day;
    private Long  value;

    public CattlePopulationDto(Date date, Long  value) {
        this.day =date;
        this.value = value;
    }


}
