package com.moaguide.controller;

import com.moaguide.dto.UserDto;
import com.moaguide.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/user")
public class LoginController {

    private final LoginService loginService;




    @GetMapping("/login")
    public String loginForm(@ModelAttribute UserDto userDto){
        return "login/login";
    }
//    @PostMapping("/login")
//    public void loginPro(@RequestBody UserDto userDto) {
//            loginService.loadUserByUsername(userDto.getUserId());
//    }

//    @GetMapping("/{custom_number}")
//    public String myPage(@PathVariable("custom_number") Long custom_number){
//        loginService.getNumber(custom_number);
//        return "mypage/mypage";
//    }

}
