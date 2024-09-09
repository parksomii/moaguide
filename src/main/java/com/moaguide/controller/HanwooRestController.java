package com.moaguide.controller;

import com.moaguide.dto.NewDto.HanwooBaseResponseDto;
import com.moaguide.dto.NewDto.HanwooPriceResponseDto;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.service.hanwoo.HanwooPriceService;
import com.moaguide.service.hanwoo.HanwooService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/hanwoo/")
public class HanwooRestController {
    private final HanwooService hanwooService;
    private final HanwooPriceService hanwooPriceService;

    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        HanwooPublishDto hanwoo = hanwooService.findHanwoo(product_Id);
        HanwooFarmDto farm = hanwooService.findFarm(product_Id);
        return ResponseEntity.ok(new HanwooBaseResponseDto(hanwoo,farm));
    }

    @GetMapping("sub/hanwooPrice")
    public ResponseEntity<Object> add(@RequestParam String category) {
        List<AveragePriceDto> averagePrice = null;
        List<Grade1RateDto> grade1Rate = null;
        List<ProductionCostDto> productionCost = null;
        if (category.equals("grade1Rate")) {
            hanwooPriceService.findGrade1Rate(category);
        }else if (category.equals("productionCost")) {  // 두당 생산비
            hanwooPriceService.findProductionCost(category);
        }else {
            if (category.equals("averagePrice")) {   // 두당 평균 도매가격
                hanwooPriceService.findAveragePrice(category);
            }
            // 거세우 평균가격
            hanwooPriceService.findAverageCattlePrice(category);
        }
        return ResponseEntity.ok().body(new HanwooPriceResponseDto(averagePrice, grade1Rate, productionCost));
    }
}
