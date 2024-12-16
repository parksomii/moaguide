package com.moaguide.controller;

import com.moaguide.domain.user.User;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/moaguide/admin")
public class AdminRestController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity adminlogin(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) {
        Boolean result = userService.checkPassword(user.getNickname(), user.getPassword());
        if(result){
            User userdata = userService.findByNickname(user.getNickname());
            if(userdata == null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 실패");
            }
            String newAccess = jwtUtil.createJwt("access",userdata.getNickname(),userdata.getRole().toString(),24 * 60 * 60 * 1000L);
            response.setHeader("Authorization", "Bearer " + newAccess);
            return ResponseEntity.ok().body("로그인 성공");
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 실패");
        }
    }
}
