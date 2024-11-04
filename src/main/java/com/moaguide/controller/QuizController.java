package com.moaguide.controller;

import com.moaguide.domain.quiz.Quiz;
import com.moaguide.dto.NewDto.customDto.QuestionDto;
import com.moaguide.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
