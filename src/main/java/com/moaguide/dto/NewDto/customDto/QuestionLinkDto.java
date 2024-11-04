package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionLinkDto {
    private String explanation;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String choice5;
    private String link;
}
