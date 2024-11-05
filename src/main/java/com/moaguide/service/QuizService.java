package com.moaguide.service;

import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.moaguide.domain.quiz.*;
import com.moaguide.dto.NewDto.customDto.QuestionCheckResponseDto;
import com.moaguide.dto.NewDto.customDto.QuestionDto;
import com.moaguide.dto.NewDto.customDto.QuestionLinkDto;
import com.moaguide.dto.NewDto.customDto.QuizRankDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;


@AllArgsConstructor
@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizResponseRepository history;
    private final QuzeHistoryRepository quzeHistory;


    public Quiz findQuiz() {
        Pageable pageable = Pageable.ofSize(1);
        return quizRepository.findQuiz(pageable).get(0);
    }

    public List<QuestionDto> findquestion(long id, String type) {
        switch (type) {
            case "b":
                return questionRepository.findByTypeB(id);
            case "c":
                return questionRepository.findByTypeC(id);
            default:
                return questionRepository.findByTypeA(id);
        }
    }

    public List<QuestionCheckResponseDto> Checkquestion(long id, String type) {
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

    @Async
    public void insertUserRank(String nickname, LocalTime time, String naver, String insta, int score, long id) {
        quzeHistory.save(new QuizeHistory(nickname,score,naver,insta,time,id));
    }

    @Async
    public void insertUserAnswer(String nickname, List<Integer> answer, long quizId) {
        String answers = answer.toString();
        history.save(new QuizResponse(nickname,answers,quizId));
    }

    public List<QuizRankDto> findrank() {
        Pageable pageable = PageRequest.of(0,5);
        return quzeHistory.findtop5(pageable);
    }
}
