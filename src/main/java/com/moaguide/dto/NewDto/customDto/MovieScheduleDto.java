package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieScheduleDto {
    private String title;
    private String genre;
    private String country;
    private String director;
    private Date releseDate;
    private String imgLink;
}
