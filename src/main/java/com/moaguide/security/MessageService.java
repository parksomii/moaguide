package com.moaguide.security;

import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final RedisTemplate<String, String> redisTemplate;

    // coolSMS API 키 및 시크릭 키
    // CoolSMS API 설정값을 application.yml에서 주입
    @Value("${spring.data.redis.coolsms.apiKey}")
    private String api_Key;

    @Value("${spring.data.redis.coolsms.apiSecret}")
    private String api_Secret;

    @Value("${spring.data.redis.coolsms.senderNumber}")
    private String sender_Number;

    private static final long CODE_EXPIRATION_TIME = 180L; // 인증 코드 유효 시간 3분

    // 인증 코드 생성
    public String generateVerificationCode() {
        // 6자리 인증 코드 생성
        return String.valueOf((int) ((Math.random() * 899999) + 100000));
    }
/*  public String createMessage(String phone) {
        String authCode = "";
        for (int i = 0; i < 6; i++) {
            authCode += (int) (Math.random() * 10);
        }
        redisTemplate.opsForValue().set(phone, authCode);
        return authCode;
    }*/
/*    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 코드 생성
        return String.valueOf(code);
    }*/

    // Redis에 인증 코드 저장
    public void saveCodeToRedis(String phoneNumber, String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        // 인증 코드 저장
        ops.set(phoneNumber + ":code", code, CODE_EXPIRATION_TIME, TimeUnit.SECONDS);
        // 생성 시간 저장
        ops.set(phoneNumber + ":timestamp", String.valueOf(Instant.now().getEpochSecond()), CODE_EXPIRATION_TIME, TimeUnit.SECONDS);
    }
    /*public void saveCodeToRedis(String phone, String code) {
        String key = "authCode:" + phone;
        redisTemplate.opsForValue().set(key, code, Duration.ofMinutes(3)); // 3분간 유효
    }*/

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
    public void verifyCode(String phone, String code) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // Redis에서 저장된 인증 코드 및 타임스탬프 가져오기
        String storedCode = ops.get(phone + ":code");
        String timestampStr = ops.get(phone + ":timestamp");

        if (storedCode == null || timestampStr == null) {
            throw new CustomSMSException("인증 코드가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        long timestamp = Long.parseLong(timestampStr);
        long currentTimestamp = Instant.now().getEpochSecond();

        // 유효 시간 초과 여부 확인
        if (currentTimestamp - timestamp > CODE_EXPIRATION_TIME) {
            throw new CustomSMSException("인증 코드의 유효 시간이 초과되었습니다.", HttpStatus.BAD_REQUEST);
        }

        // 인증 코드 일치 여부 확인
        if (!storedCode.equals(code)) {
            throw new CustomSMSException("잘못된 인증 코드입니다.", HttpStatus.BAD_REQUEST);
        }

        // 인증 성공 시 추가 작업 (예: 사용자 정보 저장, JWT 토큰 발급 등) 수행 가능
    }
}
