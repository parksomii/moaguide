package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionResponseDto {
    private List<Integer> answer;
    private String insta;
    private String naver;
    private LocalTime time;
    private String type;
}
