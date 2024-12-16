package com.moaguide.controller;


import com.moaguide.domain.billing.PaymentLog;
import com.moaguide.dto.NewDto.customDto.billingDto.LocalSubscriptDateDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.LocalBillingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/billing")
@Profile("local")
public class LocalBillingRestController {
    private final JWTUtil jwtUtil;
    private final LocalBillingService LocalbillingService;
    @Value("${toss.secretkey}")
    private String secretkey;

    public LocalBillingRestController(JWTUtil jwtUtil, LocalBillingService LocalbillingService) {
        this.jwtUtil = jwtUtil;
        this.LocalbillingService = LocalbillingService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> Billingstart(@RequestHeader(value = "Authorization",required = false) String jwt, @RequestParam(value = "couponId", required = false) Long couponId) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 만료되었습니다.");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            LocalSubscriptDateDto date = LocalbillingService.findDate(nickname);
            if(date.getPaymentDate() != null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 구독중입니다.");
            }
            LocalDateTime endDay = date.getEndDate() != null ? date.getEndDate() : null;
            if(endDay == null){
                if(couponId != null) {
                    LocalbillingService.startWithCoupon(nickname,couponId);
                }else {
                    LocalbillingService.start(nickname, Base64.getEncoder().encodeToString((secretkey + ":").getBytes()));
                }
            }else {
                LocalbillingService.startWithDate(nickname, endDay);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("카드키가 없습니다.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> Billinglist(@RequestHeader(value = "Authorization",required = false) String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 만료되었습니다.");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            List<PaymentLog> paymentLog = LocalbillingService.findPayment(nickname);
            Map<String,Object> map = new HashMap<>();
            map.put("log",paymentLog);
            return ResponseEntity.ok(map);
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> BillingStatus(@RequestHeader(value = "Authorization",required = false) String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 만료되었습니다.");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            LocalSubscriptDateDto date = LocalbillingService.findDate(nickname);
            if(date == null) {
                Map<String,Object> map = new HashMap<>();
                map.put("status",false);
                map.put("date", null);
                return ResponseEntity.ok(map);
            }else {
                Map<String,Object> map = new HashMap<>();
                map.put("status",true);
                map.put("date", date);
                return ResponseEntity.ok(map);
            }
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/stop")
    public ResponseEntity<?> Billingstop(@RequestHeader(value = "Authorization",required = false) String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 만료되었습니다.");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            LocalbillingService.stop(nickname);
            return ResponseEntity.ok().body("Success");
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/develop/delete")
    public ResponseEntity<?> BillingdevelopDelete(@RequestHeader(value = "Authorization",required = false) String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 만료되었습니다.");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            LocalbillingService.developstop(nickname);
            return ResponseEntity.ok().body("Success");
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
