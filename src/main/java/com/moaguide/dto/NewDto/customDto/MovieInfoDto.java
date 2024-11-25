package com.moaguide.dto.NewDto.customDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MovieInfoDto {
    // 영화소개
    private String movieInfo;
    // 장르
    private String subgenre;
    // 개봉일
    private Date releaseDate;
    // 등급분류
    private String grade;
    // 상영시간
    private Integer runningTime;
    // 감독
    private String director;
    // 출연
    private List<String> actor;
    // 배급사
    private String distributor;
    // 원자정보
    private String originalInfo;

    public MovieInfoDto(String movieInfo,String subgenre,Date releaseDate,String grade,Integer screeningTime,String director,String actor,String distributor,String originalInfo){
        this.movieInfo = movieInfo;
        this.subgenre = subgenre;
        this.releaseDate = releaseDate;
        this.grade = grade;
        this.runningTime = screeningTime;
        this.director = director;
        this.distributor = distributor;
        this.originalInfo = originalInfo;
        this.actor = new ArrayList<>();
        for (String actorString:actor.split(",")) {
            this.actor.add(actorString.trim());
        }
    }
}
