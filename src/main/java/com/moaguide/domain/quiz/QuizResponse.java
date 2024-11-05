package com.moaguide.domain.quiz;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuizResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // final 제거
    private String nickname;
    private String answer;
    private long quizId;
    private String type;

    // 필요한 필드만 초기화하는 생성자 추가
    public QuizResponse(String nickname, String answer, long quizId, String type) {
        this.nickname = nickname;
        this.answer = answer;
        this.quizId = quizId;
        this.type = type;
    }
}
