package com.moaguide.controller;

import com.moaguide.domain.user.User;
import com.moaguide.dto.NewDto.customDto.mailDto;
import com.moaguide.dto.UserDto;
import com.moaguide.dto.codeDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.CookieService;
import com.moaguide.service.EmailService;
import com.moaguide.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final EmailService emailService;

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
            String token = jwtUtil.createJwt("verify",nickname,"pass", 1000 * 60 * 30L);
            return ResponseEntity.ok().header("verify",  token).body("인증에 성공했습니다.");

        } else {
            return ResponseEntity.badRequest().body("fail");
        }
    }

    // 비밀번호 변경
    @PatchMapping("/update/password")
    public ResponseEntity<?> updatePassword(HttpServletRequest request, @RequestBody UserDto userDto) {
        String verifyToken = request.getHeader("verify");
        // JWT 토큰 검증
        if (jwtUtil.isExpired(verifyToken) && !jwtUtil.getRole(verifyToken).equals("pass")) {
            return new ResponseEntity<>("앞선 인증을 완료해주세요", HttpStatus.BAD_REQUEST);
        }else{
            if(userDto.getNickname().isEmpty()) {
                userService.updatePasswordbyEmail(userDto.getEmail(), userDto.getPassword());
                return ResponseEntity.ok("success");

            }else {
                userService.updatePassword(userDto.getNickname(), userDto.getPassword());
                return ResponseEntity.ok("success");
            }
        }
    }

    // 전화번호 변경
    @PatchMapping("/update/phone")
    public ResponseEntity<?> updatePhone(HttpServletRequest request , @RequestBody codeDto codeDto) {
        String verifyToken = request.getHeader("verify");
        // JWT 토큰 검증
        if (jwtUtil.isExpired(verifyToken) && !jwtUtil.getRole(verifyToken).equals("pass")) {
            return new ResponseEntity<>("앞선 인증을 완료해주세요", HttpStatus.BAD_REQUEST);
        }else{
            String nickname = jwtUtil.getNickname(request.getHeader("Authorization").substring(7));
            String phone = codeDto.getPhone();
            userService.updatePhone(nickname, phone);
            return ResponseEntity.ok("success");
        }
    }

    @PostMapping("/send/mail")
    public ResponseEntity<?> sendMail(@RequestParam String email) {
        String code = emailService.generateVerificationCode();
        emailService.saveCodeToRedis(email, code);
        String response = emailService.sendmail(email,code);
        // 예외 처리에 따라 적절한 HTTP 상태 코드 반환
        if (response.equals("이메일 전송 완료")) {
            return ResponseEntity.ok(response);  // 200 OK
        } else if (response.equals("이메일 메시지를 생성하는 중 오류가 발생했습니다.")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // 500 Internal Server Error
        } else if (response.equals("HTML 템플릿 파일을 읽는 중 오류가 발생했습니다.")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // 500 Internal Server Error
        } else if (response.equals("이메일을 전송하는 중 오류가 발생했습니다.")) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);  // 503 Service Unavailable
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // 500 Internal Server Error for unexpected errors
        }
    }

    @PostMapping("/verify/mail")
    public ResponseEntity<?> verifyMail(@RequestBody mailDto mailDto) {
        boolean success = emailService.verifyCode(mailDto.getEmail(),mailDto.getCode());
        if (success) {
            String token = jwtUtil.createJwt("verify", mailDto.getEmail(),"pass", 1000 * 60 * 30L);
            return ResponseEntity.ok().header("verify",  token).body("인증에 성공했습니다.");
        }
        else {
            return ResponseEntity.badRequest().body("인증에 실패했습니다.");
        }
    }

}