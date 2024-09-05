package com.moaguide.controller;

import com.moaguide.domain.user.Role;
import com.moaguide.domain.user.User;
import com.moaguide.dto.UserDto;
import com.moaguide.dto.codeDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.security.MessageService;
import com.moaguide.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/signup")
@AllArgsConstructor
@Slf4j
public class SignUpRestController {
    private final MessageService messageService;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    // 인증 코드 전송 요청 처리
    @PostMapping("/send/code")
    public ResponseEntity<String> sendCode(@RequestBody codeDto codeDto) {
        // 인증 코드 생성 및 전송
        try {
            if (codeDto.getPhone() == null) {
                return ResponseEntity.badRequest().body("인증 코드 전송에 실패하였습니다.");
            }
            String code = messageService.generateVerificationCode();
            messageService.saveCodeToRedis(codeDto.getPhone(), code);
            messageService.sendSms(codeDto.getPhone(), code);
            return ResponseEntity.ok("인증 코드가 전송되었습니다.");
        } catch (Exception e) {
            // 모든 예외에 대해 "실패" 반환
            return ResponseEntity.badRequest().body("인증 코드 전송에 실패하였습니다.");
        }
    }

    // 인증 코드 검증 요청 처리
    @PostMapping("/verify/code")
    public ResponseEntity<String> verifyCode(@RequestBody codeDto codeDto) {
        boolean success = messageService.verifyCode(codeDto.getPhone(), codeDto.getCode());
        if (success) {
            // 인증 성공 시 JWT 토큰 발급
            String token = jwtUtil.createJwt("verify", codeDto.getPhone(), "pass", 1000 * 60 * 30L);
            return ResponseEntity.ok().header("verify",  token).body("인증에 성공했습니다.");
        }
        else {
            return ResponseEntity.badRequest().body("인증에 실패했습니다.");
        }
    }

    @PostMapping("/verify/nickname")
    public ResponseEntity<String> verifyName(@RequestBody UserDto userDto){
        String nickname = userDto.getNickname();
        Optional<User> user = userService.check(nickname);
        if(user.isPresent()){
            return ResponseEntity.badRequest().body("중복된 닉네임이 있습니다.");
        }else{
            return ResponseEntity.ok().body("사용가능한 닉네임입니다.");
        }
    }

    @PostMapping()
    public ResponseEntity<String> signup(HttpServletRequest request, @RequestBody UserDto userDto){
        try {
            String verifyToken = request.getHeader("verify");
            // JWT 토큰 검증
            if (jwtUtil.isExpired(verifyToken) && !jwtUtil.getRole(verifyToken).equals("pass")) {
                return ResponseEntity.badRequest().body("회원가입 실패");
            }
            userDto.setRole(Role.USER);
            int res = userService.save(userDto);
            if(res == 1){
                return ResponseEntity.ok().body("회원가입 완료");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
            }
        } catch (UnexpectedRollbackException e) {
            log.error("Transaction rolled back unexpectedly: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
        } catch (Exception e) {
            log.error("An unexpected error occurred: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
        }
    }
}