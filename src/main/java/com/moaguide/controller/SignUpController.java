package com.moaguide.controller;

import com.moaguide.domain.user.Role;
import com.moaguide.dto.UserDto;
import com.moaguide.service.signupService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/user")
public class SignUpController {
    private final signupService Service;

    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("userRoles", Role.values());
        return "/login/signup";
    }

    @PostMapping("/signup/duplicate")
    public ResponseEntity<?> duplicate(@RequestParam String userId) {
        try {
            String result = Service.checkDuplicate(userId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }


    @Transactional
    @PostMapping("/signup")
    public String Signup(@ModelAttribute  UserDto userDto){
        log.info("유저DTO :"+userDto);
        Service.save(userDto);
        return "redirect:/user/login";
    }
}
