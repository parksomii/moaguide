package com.moaguide.controller;

import com.moaguide.dto.NewDto.HanwooBaseResponseDto;
import com.moaguide.dto.NewDto.HanwooDetailDto;
import com.moaguide.dto.NewDto.HanwooMarketResponseDto;
import com.moaguide.dto.NewDto.HanwooPriceResponseDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.hanwoo.HanwooPriceService;
import com.moaguide.service.hanwoo.HanwooService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok().body(hanwooDetail);
    }

    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        HanwooBaseResponseDto hanwoo = hanwooService.findDetail(product_Id);
        return ResponseEntity.ok(hanwoo);
    }

    @GetMapping("sub/hanwooPrice")
    public ResponseEntity<Object> getHanwooPriceData(@RequestParam String category, @RequestParam String date) {
        HanwooPriceResponseDto response = hanwooPriceService.getHanwooPriceData(category, date);

        if (response == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // 모든 데이터가 null 또는 empty인 경우 처리
        if ((response.getAveragePrice() == null || response.getAveragePrice().isEmpty()) &&
                (response.getGrade1Rate() == null || response.getGrade1Rate().isEmpty()) &&
                (response.getProductionCost() == null || response.getProductionCost().isEmpty())) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("sub/hanwoomarket")
    public ResponseEntity<?> getHanwooMarket(@RequestParam String category, @RequestParam String date) {
        HanwooMarketResponseDto hanwooMarket = hanwooPriceService.findHanwooMarket(category, date);

        if (hanwooMarket == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if ((hanwooMarket.getCattlePopulation() == null || hanwooMarket.getCattlePopulation().isEmpty()) &&
                (hanwooMarket.getCattleSale() == null || hanwooMarket.getCattleSale().isEmpty()) &&
                (hanwooMarket.getCattleFarm() == null || hanwooMarket.getCattleFarm().isEmpty()) &&
                (hanwooMarket.getCattlePrice() == null || hanwooMarket.getCattlePrice().isEmpty())) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(hanwooMarket);
    }
}
