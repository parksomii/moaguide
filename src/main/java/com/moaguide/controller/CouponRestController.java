package com.moaguide.controller;

import com.moaguide.domain.coupon.CouponUser;
import com.moaguide.dto.NewDto.customDto.Coupon.CouponUserDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.CouponService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/coupon")
public class CouponRestController {
    private final CouponService couponService;
    private final JWTUtil jwtUtil;

    //쿠폰 발급 API
    @PostMapping("/issue")
    public ResponseEntity issue(@RequestParam int month, @RequestParam String nickname) {
        Boolean res = couponService.create(month,nickname);
        if(res){
            return ResponseEntity.ok().body("success");
        }else {
            return ResponseEntity.internalServerError().body("fail");
        }
    }

    //쿠폰 등록 API
    @PostMapping("/register")
    public ResponseEntity register(@RequestParam String code, HttpServletRequest request) {
        try {
            String jwt = request.getHeader("Authorization");
            if (jwt == null || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            if (jwtUtil.isExpired(jwt)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String nickname = jwtUtil.getNickname(jwt);
            int res = couponService.valid(code,nickname);
            switch (res) {
                case -1:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("쿠폰이 잘못 입력되었습니다.");
                case 1:
                    return ResponseEntity.ok().body("success");
                case -2:
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("쿠폰이 이미 존재합니다.");
                default:
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
            }
        }catch (JwtException je){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("fail");
        }
    }

    //쿠폰 리스트 API
    @PostMapping("/list")
    public ResponseEntity list(HttpServletRequest request) {
        try {
            String jwt = request.getHeader("Authorization");
            if (jwt == null || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            if (jwtUtil.isExpired(jwt)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
            String nickname = jwtUtil.getNickname(jwt);
            List<CouponUserDto> list = couponService.findByNickname(nickname);
            if(!list.isEmpty() || list.size()>0 ){
                Map<String,Object> map = new HashMap<>();
                map.put("coupons",list);
                return ResponseEntity.ok().body(map);
            }else{
                Map<String,Object> map = new HashMap<>();
                map.put("coupons",new ArrayList<>());
                return ResponseEntity.ok().body(map);
            }
        }catch (JwtException je){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("fail");
        }
    }

}
