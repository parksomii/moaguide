package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class QuizHistoryDto {
    private int score;
    private String naver;
    private String insta;
    private LocalTime time;
}
