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
public class MovieScheduleDto {
    private String title;
    private String genre;
    private String country;
    private String director;
    @Nullable
    private Date releaseDate;
    @Nullable
    private String imgLink;

    public MovieScheduleDto(String title, String genre, String country, String director, Date releaseDate, String imgLink) {
        this.title = title;
        this.genre = genre;
        this.country = country;
        this.director = director;
        this.releaseDate = releaseDate;
        this.imgLink = imgLink;
    }
}