package com.moaguide.domain.quiz;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String explanation;

    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String choice5;

    private String link;

    private Integer score;

    private String type;

    private Integer solution;

    @Column(name = "quiz_id")
    private Long quizId;
}
