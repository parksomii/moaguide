package com.moaguide.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signup/")
public class SignUpRestController {

    @PostMapping("phone")
    public String phone(String phone) {
        return "발송완료";
    }

}