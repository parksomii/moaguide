package com.moaguide.refactor.contents.entity.movie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Entity
@Table(name = "Movie_Schedule")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedStoredProcedureQuery(
        name = "GetMoviesIntenDateRange",
        procedureName = "GetMoviesInDateRange",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "movieId", type = String.class)
        },
        resultClasses = MovieSchedule.class
)
public class MovieSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String genre;

    private String country;

    private String director;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "img_link")
    private String imgLink;

}
