package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CattleFarmDto {
    private LocalDate day;
    private Long  value;

    public CattleFarmDto(LocalDate date, Long  value) {
        this.day = date;
        this.value = value;
    }


}
