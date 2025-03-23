package com.moaguide.refactor.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class QuizHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private int score;
    private String naver;
    private String insta;
    private LocalTime time;
    @Column(name = "quiz_Id")
    private Long quizId;
    private int fail;

    public QuizHistory(String nickname, int score, String naver, String insta, LocalTime time, long quizId, int fail) {
        this.nickname = nickname;
        this.score = score;
        this.naver = naver;
        this.insta = insta;
        this.time = time;
        this.quizId = quizId;
        this.fail = fail;
    }
}
