package com.moaguide.controller;

import com.moaguide.security.CustomSMSException;
import com.moaguide.security.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signup/")
@AllArgsConstructor
public class SignUpRestController {

    private final MessageService messageService;

    @PostMapping("phone")
    public String phone(String phone) {
        return "발송완료";
    }

    // 인증 코드 전송 요청 처리
    @PostMapping("send-code")
    public ResponseEntity<String> sendCode(@RequestParam String phone) {
        // 인증 코드 생성 및 전송
        String code = messageService.generateVerificationCode();
        messageService.saveCodeToRedis(phone, code);
        messageService.sendSms(phone, code);
        return ResponseEntity.ok("인증 코드가 전송되었습니다.");
    }

    // 인증 코드 검증 요청 처리
    @PostMapping("verify-code")
    public ResponseEntity<String> verifyCode(@RequestParam String phone, @RequestParam String code) {
        try {
            // 인증 코드 검증
            messageService.verifyCode(phone, code);
            // 인증 성공 처리
            return ResponseEntity.ok("인증에 성공했습니다.");
        } catch (CustomSMSException e) {
            // 인증 실패 시 예외 메시지와 상태 코드 반환
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}