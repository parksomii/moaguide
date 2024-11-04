package com.moaguide.service;

import com.moaguide.domain.quiz.QuestionRepository;
import com.moaguide.domain.quiz.Quiz;
import com.moaguide.domain.quiz.QuizRepository;
import com.moaguide.dto.NewDto.customDto.QuestionCheckResponseDto;
import com.moaguide.dto.NewDto.customDto.QuestionDto;
import com.moaguide.dto.NewDto.customDto.QuestionLinkDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;


    public Quiz findQuiz() {
        Pageable pageable = Pageable.ofSize(1);
        return quizRepository.findQuiz(pageable).get(0);
    }

    public List<QuestionDto> findquestion(Integer id, String type) {
        switch (type) {
            case "b":
                return questionRepository.findByTypeB(id);
            case "c":
                return questionRepository.findByTypeC(id);
            default:
                return questionRepository.findByTypeA(id);
        }
    }

    public List<QuestionCheckResponseDto> Checkquestion(Integer id, String type) {
        switch (type) {
            case "b":
                return questionRepository.findByTypeBcheck(id);
            case "c":
                return questionRepository.findByTypeCcheck(id);
            default:
                return questionRepository.findByTypeAcheck(id);
        }
    }

    public List<QuestionLinkDto> link(List<Long> faillist) {
        return questionRepository.findByFailId(faillist);
    }
}
