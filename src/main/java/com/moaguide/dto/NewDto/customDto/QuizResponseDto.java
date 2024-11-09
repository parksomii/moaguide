package com.moaguide.dto.NewDto.customDto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QuizResponseDto {
    private Long quizId;
    private String type;
    private String failList;
    private String fail_answer;
}
