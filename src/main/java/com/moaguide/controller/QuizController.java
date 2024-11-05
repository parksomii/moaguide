package com.moaguide.controller;

import com.moaguide.domain.quiz.Quiz;
import com.moaguide.domain.quiz.QuizHistory;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private final JWTUtil jwtUtil;

    @GetMapping()
    public ResponseEntity<?> quiz() {
        Quiz quiz = quizService.findQuiz();
        Map<String,Object> map = new HashMap<>();
        map.put("quiz",quiz);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> quizById(@PathVariable long id,@RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String nickname = jwtUtil.getNickname(token);
        Boolean overlap = quizService.findoverlap(nickname,id);
        if(overlap == true){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 참여했습니다.");
        }
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

    @PostMapping("/{quizId}")
    public ResponseEntity<?> quizSubmit(@PathVariable long quizId,@RequestBody QuestionResponseDto question,@RequestHeader(value = "Authorization",required = false) String auth) {
        String nickname;
        if ( auth!= null && auth.startsWith("Bearer ")) {
            if(jwtUtil.isExpired(auth.substring(7))){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            nickname = jwtUtil.getNickname(auth.substring(7));
        }else{
            nickname = "null";
        }
        int score = 0;
        Boolean insta = false;
        Boolean naver = false;
        quizService.insertUserAnswer(nickname,question.getAnswer(),quizId,question.getType());
        if(!question.getInsta().isEmpty() && !question.getInsta().equals("null")) {
            insta = true;
            score += 5;
        }

        if(!question.getNaver().isEmpty() && !question.getNaver().equals("null")) {
            naver = true;
            score += 5;
        }
        List<Long> faillist = new ArrayList<>();
        List<Integer> failanswer = new ArrayList<>();
        List<QuestionCheckResponseDto> questionDtos = quizService.Checkquestion(quizId,question.getType());
        for(int i=0;i<questionDtos.size();i++) {
            Boolean Response = question.getAnswer().get(i) == questionDtos.get(i).getSolution();
            if (Response) {
                score += questionDtos.get(i).getScore();
            } else {
                faillist.add(questionDtos.get(i).getQuestionId());
                failanswer.add(question.getAnswer().get(i));
            }
        }
        quizService.insertUserRank(nickname,question.getTime(),question.getNaver(),question.getInsta(),score,quizId,faillist.size());
        Map<String,Object> map = new HashMap<>();
        map.put("score",score);
        map.put("insta",insta);
        map.put("naver",naver);
        if (faillist.size() > 0) {
            List<QuestionLinkDto> questionCheckResponseDtos = quizService.link(faillist);
            map.put("faillist",questionCheckResponseDtos);
            map.put("failanswer",failanswer);
            return ResponseEntity.ok(map);
        }else {
            map.put("fail", new ArrayList<>());
            return ResponseEntity.ok(map);
        }
    }

    @GetMapping("rank")
    public ResponseEntity<?> quizRank() {
        List<QuizRankDto> quizRankDtos = quizService.findrank();
        Map<String,Object> map = new HashMap<>();
        map.put("Ranking",quizRankDtos);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/rank/detail")
    public ResponseEntity<?> quizRankDetail(@RequestHeader(value = "Authorization",required = false) String auth) {
        String nickname;
        if ( auth!= null && auth.startsWith("Bearer ")) {
            if(jwtUtil.isExpired(auth.substring(7))){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            nickname = jwtUtil.getNickname(auth.substring(7));
        }else{
            nickname = "null";
        }
        double avag = quizService.findAvarage();
        Map<String,Object> map = new HashMap<>();
        map.put("avag",avag);
        QuizHistory quizRankDtos = quizService.findrankbyNickname(nickname);
        if(quizRankDtos != null) {
            map.put("nickname",nickname);
            map.put("time",quizRankDtos.getTime());
            map.put("score",quizRankDtos.getScore());
            map.put("fail",quizRankDtos.getFail());
            int count = 0;
            if(!quizRankDtos.getInsta().isEmpty() && !quizRankDtos.getInsta().equals("null")) {
                count+=1;
            }
            if(!quizRankDtos.getNaver().isEmpty() && !quizRankDtos.getNaver().equals("null")) {
                count+=1;
            }
            map.put("plus",count);
        }else{
            map.put("nickname",nickname);
            map.put("time",null);
            map.put("fail", null);
            map.put("score", null);
            map.put("plus", null);
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/rank/list")
    public ResponseEntity<?> quizRankList(@RequestParam(defaultValue = "0") long id) {
        List<QuizRankDto> quisrank = quizService.findRankList(id);
        Map<String,Object> map = new HashMap<>();
        map.put("Ranking",quisrank);
        return ResponseEntity.ok(map);
    }
}
