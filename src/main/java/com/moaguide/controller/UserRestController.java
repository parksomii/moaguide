package com.moaguide.controller;

import com.moaguide.domain.user.User;
import com.moaguide.dto.ProfileDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/User")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @GetMapping("/")
    public ResponseEntity<?> profile(@RequestHeader("Authorization") String auth) {
        String nickname = jwtUtil.getNickname(auth.substring(7));
        ProfileDto userProfile = userService.getUserNickName(nickname);
        return ResponseEntity.ok(userProfile);
    }
}
