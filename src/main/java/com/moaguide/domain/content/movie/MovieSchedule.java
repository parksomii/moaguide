package com.moaguide.domain.content.movie;

import com.moaguide.dto.NewDto.customDto.MovieScheduleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Table(name = "Movie_Schedule")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 100)
    private String genre;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String director;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "img_link", length = 255)
    private String imgLink;

}
