package com.moaguide.security;

import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    private final RedisTemplate<String, String> redisTemplate;

    // coolSMS API 키 및 시크릭 키
    // CoolSMS API 설정값을 application.yml에서 주입
    @Value("${spring.data.redis.coolsms.apiKey}")
    private String api_Key;

    @Value("${spring.data.redis.coolsms.apiSecret}")
    private String api_Secret;

    @Value("${spring.data.redis.coolsms.senderNumber}")
    private String sender_Number;


    private static final long CODE_EXPIRATION_TIME = 7200L;  // 인증 코드 유효 시간 (초) 2시간
    private static final long CODE_VALIDATION_TIME = 180;  // 인증 코드 검증 유효 시간 (초) 3분

    // 인증 코드 생성
    public String generateVerificationCode() {
        // 6자리 인증 코드 생성
        return String.valueOf((int) ((Math.random() * 899999) + 100000));
    }


    // Redis에 인증 코드 저장
    public void saveCodeToRedis(String phoneNumber, String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        long currentTimestamp = Instant.now().getEpochSecond();
        // 인증 코드 저장
        ops.set(phoneNumber + ":code", code, CODE_EXPIRATION_TIME, TimeUnit.SECONDS);
        // 생성 시간 저장
        ops.set(phoneNumber + ":timestamp", String.valueOf(currentTimestamp), CODE_EXPIRATION_TIME, TimeUnit.SECONDS);
    }
    // CoolSMS API로 인증번호 전송
    public void sendSms(String phone, String code) {
        // API 키 또는 시크릿 키가 설정되지 않았을 경우 예외 처리
        if (api_Key == null || api_Secret == null || sender_Number == null) {
            System.out.println("API 키 또는 시크릿 키가 설정되지 않았습니다.");
            return;
        }

        Message coolsms = new Message(api_Key, api_Secret);

        HashMap<String, String> params = new HashMap<>();
        params.put("to", phone);
        params.put("from", sender_Number);  // 발신 번호
        params.put("type", "SMS");
        params.put("text", "[모아가이드(주)]본인인증 번호는 " + code + "입니다. 정확히 입력해주세요.");
        params.put("app_version", "Java SDK 2.2");

        try {
            JSONObject response = coolsms.send(params);
            System.out.println(response.toJSONString());
        } catch (CoolsmsException e) {
            System.out.println("Error code: " + e.getCode() + ", Message: " + e.getMessage());
        }
    }

    // Redis에 저장된 인증 코드 확인
    public boolean verifyCode(String phone, String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // Redis에서 저장된 인증 코드 및 타임스탬프 가져오기
        String storedCode = ops.get(phone + ":code");
        String timestampStr = ops.get(phone + ":timestamp");

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
}
