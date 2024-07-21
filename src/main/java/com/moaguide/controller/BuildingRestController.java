package com.moaguide.controller;

import com.moaguide.domain.building.population.Population;
import com.moaguide.domain.building.rent.Rent;
import com.moaguide.domain.building.subway.SubwayTime;
import com.moaguide.domain.building.subway.SubwayWeek;
import com.moaguide.domain.building.vacancyrate.VacancyRate;
import com.moaguide.service.building.*;
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
@RequestMapping("/summary/detail/building/")
public class BuildingRestController {
    private final SubwayTimeService subwayTimeService;
    private final SubwayWeekService subwayWeekService;
    private final PopulationService populationService;
    private final RentService rentService;
    private final VacancyRateService vacancyRateService;

    @GetMapping("subway/{keyword}")
    public ResponseEntity<Object> Subway(@PathVariable String keyword, @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        List<SubwayWeek> subwayWeek = subwayWeekService.findbydate(keyword, year, month);
        SubwayTime subwayTime = subwayTimeService.findbydate(keyword, year, month);

        Map<String, Object> response = new HashMap<>();
        response.put("subwayTime", subwayTime);
        response.put("subwayWeek", subwayWeek);
        return ResponseEntity.ok(response);
    }

    @GetMapping("population/{id}")
    public ResponseEntity<Object> population(@PathVariable int id, @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        List<Population> populations = populationService.findbydate(id, year, month);
        return ResponseEntity.ok(populations);
    }

    @GetMapping("business/{keyword}")
    public ResponseEntity<Object> business(@PathVariable String keyword, @RequestParam(required = false) String type) {
        log.info("keyword: {}", keyword);
        log.info("type: {}", type);
        List<Rent> rent = rentService.findBase(keyword, type);
        List<VacancyRate> vacancyRate = vacancyRateService.findBase(keyword, type);
        Map<String, Object> response = new HashMap<>();
        response.put("rent", rent);
        response.put("vacancyRate", vacancyRate);
        return ResponseEntity.ok(response);
    }
}
