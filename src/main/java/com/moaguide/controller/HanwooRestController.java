package com.moaguide.controller;

import com.moaguide.dto.NewDto.HanwooBaseResponseDto;
import com.moaguide.dto.NewDto.HanwooPriceResponseDto;
import com.moaguide.dto.NewDto.customDto.HanwooFarmDto;
import com.moaguide.dto.NewDto.customDto.HanwooPublishDto;
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

    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        HanwooBaseResponseDto hanwoo = hanwooService.findDetail(product_Id);
        return ResponseEntity.ok(hanwoo);
    }

    @GetMapping("sub/hanwooPrice")
    public ResponseEntity<Object> getHanwooPriceData(@RequestParam String category) {
        HanwooPriceResponseDto response = hanwooPriceService.getHanwooPriceData(category);

        if (response == null) {
            return ResponseEntity.badRequest().body("잘못된 카테고리입니다.");
        }

        // 모든 데이터가 null 또는 empty인 경우 처리
        if ((response.getAveragePrice() == null || response.getAveragePrice().isEmpty()) &&
                (response.getGrade1Rate() == null || response.getGrade1Rate().isEmpty()) &&
                (response.getProductionCost() == null || response.getProductionCost().isEmpty())) {
            return ResponseEntity.badRequest().body("데이터가 존재하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }
}
