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


    // 닉네임 수정
    @PatchMapping("/update/nickname")
    public ResponseEntity<?> updateNickname(@RequestHeader("Authorization") String auth, @RequestBody UserDto userDto,HttpServletResponse response) {
        String findNickname = jwtUtil.getNickname(auth.substring(7));
        String changeNickname = userDto.getNickname();
        User changeuser = userService.updateNickname(findNickname, changeNickname);
        if(changeuser == null){
            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
        }
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
    @PostMapping("/check/password")
    public ResponseEntity<?> checkPassword(@RequestHeader("Authorization") String auth, @RequestBody UserDto userDto) {
        String nickname = jwtUtil.getNickname(auth.substring(7));
        String password = userDto.getPassword();
        boolean check = userService.checkPassword(nickname, password);
        if (check) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.badRequest().body("fail");
        }
    }

    // 비밀번호 변경
    @PatchMapping("/update/password")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String auth, @RequestBody UserDto userDto) {
        String nickname = jwtUtil.getNickname(auth.substring(7));
        String password = userDto.getPassword();
        userService.updatePassword(nickname, password);
        return ResponseEntity.ok("success");
    }
    // 전화번호 변경
    @PatchMapping("/update/phone")
    public ResponseEntity<?> updatePhone(@RequestHeader("Authorization") String auth, @RequestBody codeDto codeDto) {
        String nickname = jwtUtil.getNickname(auth.substring(7));
        String phone = codeDto.getPhone();
        userService.updatePhone(nickname, phone);
        return ResponseEntity.ok("success");
    }
}