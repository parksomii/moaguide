package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieScheduleDto {
    private String title;
    private String genre;
    private String country;
    private String director;
    @Nullable
    private Date releaseDate;
    @Nullable
    private String imgLink;
}
