package com.moaguide.controller;

import com.moaguide.dto.ProfileDto;
import com.moaguide.dto.UserDto;
import com.moaguide.dto.codeDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

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
    public ResponseEntity<?> updateNickname(@RequestHeader("Authorization") String auth, @RequestBody codeDto codeDto) {
        String findNickname = jwtUtil.getNickname(auth.substring(7));
        String changeNickname = codeDto.getNickname();
        codeDto userNickName = userService.updateNickname(findNickname, changeNickname);
        return ResponseEntity.ok(userNickName.getNickname());
    }
    // 비밀번호 수정
    // 전화번호 수정

}