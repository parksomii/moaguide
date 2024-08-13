package com.moaguide.controller;

import com.moaguide.domain.user.User;
import com.moaguide.dto.ProfileDto;
import com.moaguide.dto.UserDto;
import com.moaguide.dto.codeDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.CookieService;
import com.moaguide.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final CookieService cookieService;

    // 마이페이지
    @GetMapping()
    public ResponseEntity<?> profile(@RequestHeader("Authorization") String auth) {
        String nickname = jwtUtil.getNickname(auth.substring(7));
        ProfileDto userProfile = userService.getUserNickName(nickname);
        return ResponseEntity.ok(userProfile);
    }

    // 마이페이지 수정
    // 닉네임 수정
    @PutMapping("/update/nickname")
    public ResponseEntity<?> updateNickname(@RequestHeader("Authorization") String auth, @RequestBody codeDto codeDto,HttpServletResponse response) {
        String findNickname = jwtUtil.getNickname(auth.substring(7));
        String changeNickname = codeDto.getNickname();
        User changeuser = userService.updateNickname(findNickname, changeNickname);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", changeuser.getNickname(), changeuser.getRole().name(), 30 * 60 * 1000L); // 30분
        long refreshTokenValidity = 6 * 30 * 24 * 60 * 60 * 1000L; // 6달 또는 5시간
        String refreshToken = jwtUtil.createJwt("refresh", changeuser.getNickname(), changeuser.getRole().name(), refreshTokenValidity);

        //response
        response.setHeader("Authorization", "Bearer " + newAccess);
        response .addCookie(cookieService.createCookie("refresh", refreshToken, refreshTokenValidity));
        return new ResponseEntity<>(changeuser.getNickname(), HttpStatus.OK);
    }
    // 비밀번호 인증

    // 비밀번호 변경

    // 전화번호 변경

}