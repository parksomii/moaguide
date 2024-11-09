package com.moaguide.domain.quiz;

import jakarta.persistence.*;
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
    @Column(name = "quiz_Id")
    private long quizId;
    private String type;
    @Column(name = "fail_list")
    private String failList;
    @Column(name = "fail_answer")
    private String failAnswer;

    // 필요한 필드만 초기화하는 생성자 추가
    public QuizResponse(String nickname, String answer, long quizId, String type, String failList, String failAnswer) {
        this.nickname = nickname;
        this.answer = answer;
        this.quizId = quizId;
        this.type = type;
        this.failList = failList;
        this.failAnswer = failAnswer;
    }
}
