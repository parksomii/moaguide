package com.moaguide.controller;


import com.moaguide.domain.billding.PaymentLog;
import com.moaguide.dto.NewDto.customDto.billingDto.SubscriptDateDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.BillingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/billing")
public class BillingRestController {
    private final JWTUtil jwtUtil;
    private final BillingService billingService;
    @Value("${toss.secretkey}")
    private String secretkey;

    public BillingRestController(JWTUtil jwtUtil, BillingService billingService) {
        this.jwtUtil = jwtUtil;
        this.billingService = billingService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> Billingstart(@RequestHeader(value = "Authorization") String jwt, @RequestParam(value = "couponId", required = false) Long couponId) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            SubscriptDateDto date = billingService.findDate(nickname);
            if(date.getPaymentDate() != null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 구독중입니다.");
            }
            LocalDate endDay = date.getEndDate() != null ? date.getEndDate() : null;
            if(endDay == null){
                if(couponId != null) {
                    billingService.startWithCoupon(nickname,couponId);
                }else {
                    billingService.start(nickname, Base64.getEncoder().encodeToString((secretkey + ":").getBytes()));
                }
            }else {
                billingService.startWithDate(nickname, endDay);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND ).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> Billinglist(@RequestHeader(value = "Authorization") String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            List<PaymentLog> paymentLog = billingService.findPayment(nickname);
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
    public ResponseEntity<?> BillingStatus(@RequestHeader(value = "Authorization") String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            SubscriptDateDto date = billingService.findDate(nickname);
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
    public ResponseEntity<?> Billingstop(@RequestHeader(value = "Authorization") String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            billingService.stop(nickname);
            return ResponseEntity.ok().body("Success");
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/develop/delete")
    public ResponseEntity<?> BillingdevelopDelete(@RequestHeader(value = "Authorization") String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
            billingService.developstop(nickname);
            return ResponseEntity.ok().body("Success");
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
