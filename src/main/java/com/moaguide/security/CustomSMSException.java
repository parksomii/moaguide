package com.moaguide.security;

import org.springframework.http.HttpStatus;

// SMS 전송 예외 클래스
public class CustomSMSException extends RuntimeException {

    private HttpStatus status; // 예외 상태 코드

    // 예외 클래스 생성자
    public CustomSMSException(String msg, HttpStatus status) {
        super(msg); // 예외발생시 메세지 설정
        this.status = status;
    }

    // 상태 코드 반환
    public HttpStatus getStatus() {
        return status;
    }
}
