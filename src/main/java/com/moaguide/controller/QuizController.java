package com.moaguide.controller;

import com.moaguide.refactor.quiz.entity.Quiz;
import com.moaguide.refactor.quiz.entity.QuizHistory;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.refactor.security.jwt.JWTUtil;
import com.moaguide.service.QuizService;
import lombok.AllArgsConstructor;
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
		Map<String, Object> map = new HashMap<>();
		map.put("quiz", quiz);
		return ResponseEntity.ok(map);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> quizById(@PathVariable long id) {
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
		List<QuestionDto> questionDtos = quizService.findquestion(id, type);
		Map<String, Object> map = new HashMap<>();
		map.put("question", questionDtos);
		map.put("type", type);
		return ResponseEntity.ok(map);
	}

	@GetMapping("/check/{id}")
	public ResponseEntity<?> check(@PathVariable long id,
		@RequestHeader(value = "Authorization", required = false) String auth) {
		String token = auth.substring(7);
		String nickname = jwtUtil.getNickname(token);
		Boolean overlap = quizService.findoverlap(nickname, id);
		if (overlap) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 참여했습니다.");
		} else {
			return ResponseEntity.ok("참여한적이 없습니다.");
		}
	}

	@PostMapping("/confirm")
	public ResponseEntity<?> check(
		@RequestHeader(value = "Authorization", required = false) String auth) {
		String nickname;
		if (auth != null && auth.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(auth.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			nickname = jwtUtil.getNickname(auth.substring(7));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 안됨");
		}
		Boolean overlap = quizService.findoverlap(nickname);
		if (overlap) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미 참여했습니다.");
		} else {
			return ResponseEntity.ok("참여한적이 없습니다.");
		}
	}


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> quizDelete(@PathVariable long id,
		@RequestHeader("Authorization") String auth) {
		String nickname;
		if (auth != null && auth.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(auth.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			nickname = jwtUtil.getNickname(auth.substring(7));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("액세스 오류");
		}
		String result = quizService.deleteByNickname(nickname);
		if (result.equals("성공")) {
			return ResponseEntity.ok("성공적으로 삭제되었습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
		}
	}

	@PostMapping("/{quizId}")
	public ResponseEntity<?> quizSubmit(@PathVariable long quizId,
		@RequestBody QuestionResponseDto question,
		@RequestHeader(value = "Authorization", required = false) String auth) {
		String nickname;
		if (auth != null && auth.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(auth.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			nickname = jwtUtil.getNickname(auth.substring(7));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("액세스 오류");
		}
		int score = 0;
		Boolean insta = false;
		Boolean naver = false;
		if (!question.getInsta().isEmpty() && !question.getInsta().equals("null")) {
			insta = true;
			score += 5;
		}

		if (!question.getNaver().isEmpty() && !question.getNaver().equals("null")) {
			naver = true;
			score += 5;
		}
		List<Long> faillist = new ArrayList<>();
		List<Integer> failanswer = new ArrayList<>();
		List<QuestionCheckResponseDto> questionDtos = quizService.Checkquestion(quizId,
			question.getType());
		for (int i = 0; i < questionDtos.size(); i++) {
			Boolean Response = question.getAnswer().get(i) == questionDtos.get(i).getSolution();
			if (Response) {
				score += questionDtos.get(i).getScore();
			} else {
				faillist.add(questionDtos.get(i).getQuestionId());
				failanswer.add(question.getAnswer().get(i));
			}
		}
		quizService.insertUserAnswer(nickname, question.getAnswer(), quizId, question.getType(),
			faillist, failanswer);
		quizService.insertUserRank(nickname, question.getTime(), question.getNaver(),
			question.getInsta(), score, quizId, faillist.size());
		return ResponseEntity.ok("제출이 완료되었습니다.");
	}

	@GetMapping("/myscore")
	public ResponseEntity<?> myscore(
		@RequestHeader(value = "Authorization", required = false) String auth) {
		String nickname;
		if (auth != null && auth.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(auth.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			nickname = jwtUtil.getNickname(auth.substring(7));
		} else {
			nickname = "null";
		}

		QuizResponseDto quizresponse = quizService.findQuizResponse(nickname);
		QuizHistoryDto quizHistoryDto = quizService.findQuizHistory(nickname);
		if (quizresponse == null || quizHistoryDto == null) {
			Map<String, Object> map = new HashMap<>();
			map.put("failList", null);
			map.put("plus", null);
			map.put("score", null);
			map.put("failanswer", null);
			map.put("time", null);
			return ResponseEntity.ok(map);
		} else {
			List<QuestionLinkDto> questionDto = quizService.link(quizresponse.getQuizId(),
				quizresponse.getFailList(), quizresponse.getType());
			int plus = 0;
			if (!quizHistoryDto.getNaver().isEmpty() && !quizHistoryDto.getNaver().equals("null")) {
				plus += 5;
			}
			if (!quizHistoryDto.getInsta().isEmpty() && !quizHistoryDto.getInsta().equals("null")) {
				plus += 5;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("failList", questionDto);
			map.put("plus", plus);
			map.put("score", quizHistoryDto.getScore());
			map.put("failanswer", quizresponse.getFail_answer());
			map.put("time", quizHistoryDto.getTime());
			return ResponseEntity.ok(map);
		}


	}

	@GetMapping("rank")
	public ResponseEntity<?> quizRank() {
		List<QuizRankDto> quizRankDtos = quizService.findrank();
		Map<String, Object> map = new HashMap<>();
		map.put("Ranking", quizRankDtos);
		return ResponseEntity.ok(map);
	}

	@GetMapping("/rank/detail")
	public ResponseEntity<?> quizRankDetail(
		@RequestHeader(value = "Authorization", required = false) String auth) {
		String nickname;
		if (auth != null && auth.startsWith("Bearer ")) {
			if (jwtUtil.isExpired(auth.substring(7))) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			nickname = jwtUtil.getNickname(auth.substring(7));
		} else {
			nickname = "null";
		}
		double avag = quizService.findAvarage();
		Map<String, Object> map = new HashMap<>();
		map.put("avag", avag);
		QuizHistory quizRankDtos = quizService.findrankbyNickname(nickname);
		if (quizRankDtos != null) {
			map.put("nickname", nickname);
			map.put("time", quizRankDtos.getTime());
			map.put("score", quizRankDtos.getScore());
			map.put("fail", quizRankDtos.getFail());
			int plus = 0;
			if (!quizRankDtos.getInsta().isEmpty() && !quizRankDtos.getInsta().equals("null")) {
				plus += 5;
			}
			if (!quizRankDtos.getNaver().isEmpty() && !quizRankDtos.getNaver().equals("null")) {
				plus += 5;
			}
			map.put("plus", plus);
		} else {
			map.put("nickname", nickname);
			map.put("time", null);
			map.put("fail", null);
			map.put("score", null);
			map.put("plus", null);
		}
		return ResponseEntity.ok(map);
	}

	@GetMapping("/rank/list")
	public ResponseEntity<?> quizRankList(@RequestParam(defaultValue = "0") long id) {
		List<QuizRankDto> quisrank = quizService.findRankList(id);
		Map<String, Object> map = new HashMap<>();
		map.put("Ranking", quisrank);
		return ResponseEntity.ok(map);
	}
}
