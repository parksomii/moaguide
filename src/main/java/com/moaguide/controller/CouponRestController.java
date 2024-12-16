package com.moaguide.controller;

import com.moaguide.domain.coupon.CouponUser;
import com.moaguide.domain.user.Role;
import com.moaguide.domain.user.User;
import com.moaguide.dto.NewDto.customDto.Coupon.CouponAdminDto;
import com.moaguide.dto.NewDto.customDto.Coupon.CouponUserDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.CouponService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Slf4j
public class CouponRestController {
    private final CouponService couponService;
    private final JWTUtil jwtUtil;

    //쿠폰 발급 API
    @PostMapping("/issue")
    public ResponseEntity issue(@RequestParam int month, @RequestParam String nickname,@RequestHeader(value = "Authorization",required = false) String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 만료되었습니다.");
            }
            if(!jwtUtil.getRole(jwt.substring(7)).equals(Role.ADMIN.toString())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근이 불가능합니다.");
            }
            String res = couponService.create(month, nickname);
            if (res.equals("이메일 전송 완료")) {
                return ResponseEntity.ok().body("success");
            } else {
                return ResponseEntity.internalServerError().body(res);
            }
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에러");
        }
    }
    //쿠폰 등록 API
    @PostMapping("/register")
    public ResponseEntity register(@RequestParam String code,@RequestHeader(value = "Authorization",required = false) String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 만료되었습니다.");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
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
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에러");
        }
    }

    //쿠폰 리스트 API
    @GetMapping("/list")
    public ResponseEntity list(@RequestHeader(value = "Authorization",required = false) String jwt) {
        try {
            if (jwt == null ||!jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 만료되었습니다.");
            }
            String nickname = jwtUtil.getNickname(jwt.substring(7));
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
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에러");
        }
    }


    //쿠폰 발급 내역 API
    @GetMapping("/admin/list")
    public ResponseEntity list(@RequestParam int page, @RequestParam int size,@RequestHeader(value = "Authorization",required = false) String jwt) {
        try {
            if (jwt == null || !jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 만료되었습니다.");
            }
            if(!jwtUtil.getRole(jwt.substring(7)).equals(Role.ADMIN.toString())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근이 불가능합니다.");
            }
            Pageable pages = Pageable.ofSize(size).withPage(page - 1);
            Page<CouponAdminDto> couponAdminDtos = couponService.findByAdmin(pages);
            Map<String, Object> map = new HashMap<>();
            map.put("couponlist", couponAdminDtos.getContent());
            map.put("page", page);
            map.put("size", size);
            map.put("total", couponAdminDtos.getTotalElements());
            return ResponseEntity.ok().body(map);
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에러");
        }
    }

    @GetMapping("/admin/user/list")
    public ResponseEntity userlist(@RequestHeader(value = "Authorization",required = false) String jwt) {
        try {
            if (jwt == null || !jwt.startsWith("Bearer ") || jwt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 없습니다.");
            }
            if (jwtUtil.isExpired(jwt.substring(7))) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("액세스 토큰이 만료되었습니다.");
            }
            if(jwtUtil.getRole(jwt) != Role.ADMIN.toString()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근이 불가능합니다.");
            }
            List<String> userList = couponService.findUser();
            Map<String, Object> map = new HashMap<>();
            map.put("userlist", userList);
            return ResponseEntity.ok().body(map);
        }catch (JwtException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에러");
        }
    }
}
