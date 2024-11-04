package com.moaguide.controller;

import com.moaguide.domain.quiz.Quiz;
import com.moaguide.dto.NewDto.customDto.QuestionCheckResponseDto;
import com.moaguide.dto.NewDto.customDto.QuestionDto;
import com.moaguide.dto.NewDto.customDto.QuestionLinkDto;
import com.moaguide.dto.NewDto.customDto.QuestionResponseDto;
import com.moaguide.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping()
    public ResponseEntity<?> quiz() {
        Quiz quiz = quizService.findQuiz();
        Map<String,Object> map = new HashMap<>();
        map.put("quiz",quiz);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> quizById(@PathVariable Integer id) {
        int seed = (int) (Math.random() * 3);
        String type;
        switch (seed) {
            case 0:
                type = "a";
                break;
            case 1:
                type = "b";
                break;
            default:
                type = "c";
        }
        List<QuestionDto> questionDtos = quizService.findquestion(id,type);
        Map<String,Object> map = new HashMap<>();
        map.put("question",questionDtos);
        map.put("type",type);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> quizSubmit(@PathVariable Integer id,@RequestParam String type,@RequestBody QuestionResponseDto questionResponseDto) {
        int score = 0;
        Boolean insta = false;
        Boolean naver = false;
        if(questionResponseDto.getInsta().isEmpty() || questionResponseDto.getInsta().equals("null")) {
            insta = true;
            score += 5;
        }

        if(questionResponseDto.getNaver().isEmpty() || questionResponseDto.getNaver().equals("null")) {
            naver = true;
            score += 5;
        }
        List<Long> faillist = new ArrayList<>();
        List<QuestionCheckResponseDto> questionDtos = quizService.Checkquestion(id,type);
        for(int i=0;i<questionDtos.size();i++) {
            Boolean Response = questionResponseDto.getAnswer().get(i) == questionDtos.get(i).getSolution();
            if (Response) {
                score += questionDtos.get(i).getScore();
            } else {
                faillist.add(questionDtos.get(i).getQuestionId());
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("score",score);
        map.put("insta",insta);
        map.put("naver",naver);
        if (faillist.size() > 0) {
            List<QuestionLinkDto> questionCheckResponseDtos = quizService.link(faillist);
            map.put("faillist",questionCheckResponseDtos);
            return ResponseEntity.ok(map);
        }else {
            map.put("fail", new ArrayList<>());
            return ResponseEntity.ok(map);
        }
    }
}
