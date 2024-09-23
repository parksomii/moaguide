package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieScheduleDto {
    private String title;
    private String genre;
    private String country;
    private String director;
    private Date releaseDate;
    @Nullable
    private String imgLink;
}