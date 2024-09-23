package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
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