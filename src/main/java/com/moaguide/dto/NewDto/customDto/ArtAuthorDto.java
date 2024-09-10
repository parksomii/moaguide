package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtAuthorDto {
    // 작가 정보
    private String authorName; // 작가 이름
    private String nationality; // 국적
    private LocalDate birth; // 태생년도
    private String academicAbility; // 학력
    private String awardCareer; // 수상 경력
    private String major; // 대표작
    private String introduction; // 작가활동 및 소개

    /*public ArtAuthorDto(String authorName, String nationality, LocalDate birth, String academicAbility, String awardCareer, String major, String introduction) {
        this.authorName = authorName;
        this.nationality = nationality;
        this.birth = birth;
        this.academicAbility = academicAbility;
        this.awardCareer = awardCareer;
        this.major = major;
        this.introduction = introduction;
    }*/
}
