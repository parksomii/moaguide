package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
public class MovieSubDto {
    private Long count;
    private Date day;

    public MovieSubDto(int count,Date day){
        this.count =(long) count;
        this.day =day;
    }

    public MovieSubDto(Long count,Date day){
        this.count = count;
        this.day =day;
    }
}
