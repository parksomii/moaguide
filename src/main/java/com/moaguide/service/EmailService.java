package com.moaguide.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class EmailService {
    private final RedisTemplate<String, String> redisTemplate;
    private final JavaMailSender mailSender;

    // 인증 코드 생성
    public String generateVerificationCode() {
        // 6자리 인증 코드 생성
        return String.valueOf((int) ((Math.random() * 899999) + 100000));
    }

    private static final long CODE_VALIDATION_TIME = 300;  // 인증 코드 검증 유효 시간 (초) 3분
    // Redis에 인증 코드 저장
    public void saveCodeToRedis(String email, String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        long currentTimestamp = Instant.now().getEpochSecond();
        // 인증 코드 저장
        ops.set(email + ":code", code, CODE_VALIDATION_TIME, TimeUnit.SECONDS);
        // 생성 시간 저장
        ops.set(email + ":timestamp", String.valueOf(currentTimestamp), CODE_VALIDATION_TIME, TimeUnit.SECONDS);
    }

    // Redis에 저장된 인증 코드 확인
    public boolean verifyCode(String email, String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // Redis에서 저장된 인증 코드 및 타임스탬프 가져오기
        String storedCode = ops.get(email + ":code");
        String timestampStr = ops.get(email + ":timestamp");

        if (storedCode == null || timestampStr == null) {
            return false;
        }

        long timestamp = Long.parseLong(timestampStr);
        long currentTimestamp = Instant.now().getEpochSecond();

        // 유효 시간 초과 또는 인증 코드 불일치 시 인증 실패
        if (currentTimestamp - timestamp > CODE_VALIDATION_TIME || !storedCode.equals(code)) {
            return false;
        }
        return true; // 인증 성공
    }

    public String sendmail(String email, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8);

            helper.setTo(decodedEmail);
            helper.setSubject("모아가이드 이메일 인증");

            ClassPathResource resource = new ClassPathResource("/templates/email/sendmail.html");
            String htmlContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

            // 플레이스홀더를 실제 인증 번호로 대체
            htmlContent = htmlContent.replace("{{code}}", code);

            // 이메일 본문에 HTML 설정
            helper.setText(htmlContent, true);

            // 이메일 전송
            mailSender.send(message);
            return "이메일 전송 완료";
        } catch (MessagingException e) {
            // 이메일 생성 과정에서 발생하는 예외 처리
            System.err.println("Error creating or setting up the email message: " + e.getMessage());
            e.printStackTrace();
            return "이메일 메시지를 생성하는 중 오류가 발생했습니다.";
        } catch (IOException e) {
            // HTML 파일을 읽는 중 발생할 수 있는 IO 예외 처리
            System.err.println("Error reading the HTML template file: " + e.getMessage());
            e.printStackTrace();
            return "HTML 템플릿 파일을 읽는 중 오류가 발생했습니다.";
        } catch (MailException e) {
            // 이메일 전송 과정에서 발생하는 예외 처리
            System.err.println("Error sending the email: " + e.getMessage());
            e.printStackTrace();
            return "이메일을 전송하는 중 오류가 발생했습니다.";
        } catch (Exception e) {
            // 그 외 예상하지 못한 예외 처리
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return "예상치 못한 오류가 발생했습니다.";
        }
    }
}
