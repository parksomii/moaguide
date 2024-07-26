package com.moaguide.controller;

import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.dto.BuildingDetailDto;
import com.moaguide.dto.NewDto.DetailDivideResponseDto;
import com.moaguide.dto.NewDto.DetailReportResponseDto;
import com.moaguide.dto.NewDto.customDto.DetailNewsResponseDto;
import com.moaguide.dto.NewDto.customDto.DivideCustomDto;
import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import com.moaguide.dto.NewDto.customDto.ReportCustomDto;
import com.moaguide.service.DivideService;
import com.moaguide.service.NewsService;
import com.moaguide.service.ReportService;
import com.moaguide.service.building.BuildingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/")
public class DetailRestcontroller {
    private ReportService reportService;
    private NewsService newsService;
    private DivideService divideService;
    private BuildingService buildingService;

    @GetMapping("report/{category}")
    public ResponseEntity<Object> report(@PathVariable String category,@RequestParam int page,@RequestParam int size,@RequestParam String subCategory) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<ReportCustomDto> Report = reportService.findBylist(category,subCategory,pageable);
        return ResponseEntity.ok(new DetailReportResponseDto(Report));
    }

    @GetMapping("news/")
    public ResponseEntity<Object> news(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<NewsCustomDto> news = newsService.findBydetail(keyword,pageable);
        log.info("뉴스체크 :{}", news);
        return ResponseEntity.ok(new DetailNewsResponseDto(news));
    }

    @GetMapping("divide/{category}")
    public ResponseEntity<Object> divide(@PathVariable String category,@RequestParam String product_Id) {
        List<DivideCustomDto> divideDtos = divideService.findAllByproductId(product_Id);
        if(category.equals("building")) {
            BuildingDetail buildingDetailDto = buildingService.detail(product_Id);
            return ResponseEntity.ok(new DetailDivideResponseDto(divideDtos,buildingDetailDto.getDividendCycle()));
        }else{
            return ResponseEntity.ok(new DetailDivideResponseDto(divideDtos,1));
        }
    }
}
