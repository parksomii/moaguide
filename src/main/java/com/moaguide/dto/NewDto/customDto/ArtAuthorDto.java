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
}
