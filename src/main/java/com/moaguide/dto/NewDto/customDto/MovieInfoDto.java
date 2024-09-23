package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
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
    private String actor;
    // 배급사
    private String distributor;
    // 원자정보
    private String originalInfo;
}
