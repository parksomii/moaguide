package com.moaguide.controller;

import com.moaguide.domain.user.Role;
import com.moaguide.domain.user.User;
import com.moaguide.dto.UserDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/signup")
@AllArgsConstructor
@Slf4j
public class SignUpRestController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @PostMapping("/verify/nickname")
    public ResponseEntity<String> verifyName(@RequestBody UserDto userDto){
        String nickname = userDto.getNickname();
        Optional<User> user = userService.check(nickname);
        if(user.isPresent()){
            return ResponseEntity.badRequest().body("중복된 닉네임이 있습니다.");
        }else{
            return ResponseEntity.ok().body("사용가능한 닉네임입니다.");
        }
    }

    @PostMapping("/verify/email")
    public ResponseEntity<String> verifyEmail(@RequestBody UserDto userDto){
        boolean duplication = userService.findDuplication(userDto.getEmail());
        if(duplication){
            return ResponseEntity.badRequest().body("중복된 이메일이 있습니다.");
        }
        else {
            return ResponseEntity.ok("중복된 이메일이 없습니다.");
        }
    }

    @PostMapping()
    public ResponseEntity<String> signup(HttpServletRequest request, @RequestBody UserDto userDto){
        try {
            String verifyToken = request.getHeader("verify");
            // JWT 토큰 검증
            if (jwtUtil.isExpired(verifyToken) && !jwtUtil.getRole(verifyToken).equals("pass")) {
                return ResponseEntity.badRequest().body("회원가입 실패");
            }
            userDto.setRole(Role.USER);
            if(userDto.getMarketingConsent()==null){
                userDto.setMarketingConsent(0);
            }
            int res = userService.save(userDto);
            if(res == 1){
                return ResponseEntity.ok().body("회원가입 완료");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
            }
        } catch (UnexpectedRollbackException e) {
            log.error("Transaction rolled back unexpectedly: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
        } catch (Exception e) {
            log.error("An unexpected error occurred: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패");
        }
    }

    @PostMapping("/history/{email}")
    public ResponseEntity<String> checkHistory(@PathVariable String email){
        userService.emailHistory(email);
        return ResponseEntity.ok().body("성공했습니다.");
    }

}