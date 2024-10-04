package com.moaguide.controller;

import com.moaguide.dto.NewDto.HanwooBaseResponseDto;
import com.moaguide.dto.NewDto.HanwooDetailDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.hanwoo.HanwooPriceService;
import com.moaguide.service.hanwoo.HanwooService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/hanwoo/")
public class HanwooRestController {
    private final HanwooService hanwooService;
    private final HanwooPriceService hanwooPriceService;
    private final JWTUtil jwtUtil;

//    @GetMapping("{product_Id}")
//    public ResponseEntity<Object> detail(@PathVariable String product_Id, @RequestHeader("Authorization") String auth) {
//        String Nickname = jwtUtil.getNickname(auth.substring(7));
//        HanwooDetailDto hanwooDetail = hanwooService.findHanwooDetail(product_Id,Nickname);
//        return ResponseEntity.ok().body(hanwooDetail);
//    }

    @GetMapping("{product_Id}")
    public ResponseEntity<Object> detail(@PathVariable String product_Id) {
        String Nickname = "moaguide";
        HanwooDetailDto hanwooDetail = hanwooService.findHanwooDetail(product_Id,Nickname);
        // null 체크
        if (hanwooDetail == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        return ResponseEntity.ok().body(hanwooDetail);
    }

    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        HanwooBaseResponseDto hanwoo = hanwooService.findDetail(product_Id);
        // null 체크
        if (hanwoo == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        return ResponseEntity.ok(hanwoo);
    }

    @GetMapping("sub/hanwooPrice")
    public ResponseEntity<Object> getHanwooPriceData(@RequestParam String category, @RequestParam int month) {
        List<?> result = hanwooPriceService.getHanwooPriceData(category, month);
        Map<String, Object> response = new HashMap<>();
        // null 체크
        if (result == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        if (result.isEmpty()){
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        response.put("object",result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("sub/hanwoomarket")
    public ResponseEntity<?> getHanwooMarket(@RequestParam String category, @RequestParam int month) {
        List<?> hanwooMarket = hanwooPriceService.findHanwooMarket(category, month);
        Map<String, Object> response = new HashMap<>();
        // null 체크
        if (hanwooMarket == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        if (hanwooMarket.isEmpty()){
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        response.put("object",hanwooMarket);
        return ResponseEntity.ok(response);
    }
}
