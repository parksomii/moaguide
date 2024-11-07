package com.moaguide.service;

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
    private final QuzeHistoryRepository quizHistory;


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
    public void insertUserRank(String nickname, LocalTime time, String naver, String insta, int score, long id, int size) {
        quizHistory.save(new QuizHistory(nickname,score,naver,insta,time,id,size));
    }

    @Async
    public void insertUserAnswer(String nickname, List<Integer> answer, long quizId, String type, List<Long> faillist) {
        String answers = answer.toString();
        String fail = faillist.toString();
        history.save(new QuizResponse(nickname,answers,quizId,type,fail));
    }

    public List<QuizRankDto> findrank() {
        Pageable pageable = PageRequest.of(0,5);
        return quizHistory.findtop5(pageable);
    }

    public Boolean findoverlap(String nickname, long id) {
        return quizHistory.findByNickname(nickname,id);
    }

    public QuizHistory findrankbyNickname(String nickname) {
        return quizHistory.findBynick(nickname).orElse(null);
    }

    public double findAvarage() {
        return  quizHistory.findAvarage();
    }

    public List<QuizRankDto> findRankList(long id) {
        Pageable pageable = PageRequest.of((int) id,20);
        return quizHistory.findList(pageable);
    }

    public String deleteByNickname(String nickname) {
        try {
            quizHistory.deleteByNickname(nickname);
            history.deleteByNickname(nickname);
            return "성공";
        }catch (Exception e){
            return e+"오류 발생";
        }

    }
}
