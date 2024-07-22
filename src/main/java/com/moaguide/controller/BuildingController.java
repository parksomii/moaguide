package com.moaguide.controller;


import com.moaguide.domain.building.area.Area;
import com.moaguide.domain.building.businessarea.BusinessArea;
import com.moaguide.domain.building.landregistry.LandRegistry;
import com.moaguide.domain.building.lease.Lease;
import com.moaguide.domain.building.near.NearBus;
import com.moaguide.domain.building.near.NearSubway;
import com.moaguide.domain.building.population.Population;
import com.moaguide.domain.building.rent.Rent;
import com.moaguide.domain.building.subway.SubwayTime;
import com.moaguide.domain.building.subway.SubwayWeek;
import com.moaguide.domain.building.vacancyrate.VacancyRate;
import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.news.News;
import com.moaguide.domain.summary.Summary;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.*;
import com.moaguide.service.*;
import com.moaguide.service.building.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/summary/detail")
public class BuildingController {
    private final BuildingService buildingService;
    private final LeaseService leaseService;
    private final LandPriceService landPriceservice;
    private final NewsService newsService;
    private final PopulationService populationService;
    private final TransactionService transactionService;
    private final DivideService divideService;
    private final LocationService locationService;
    private final AreaService areaService;
    private final SummaryService summaryService;
    private final LandRegistryService landRegistryService;
    private final SubwayTimeService subwayTimeService;
    private final SubwayWeekService subwayWeekService;
    private final RentService rentService;
    private final VacancyRateService vacancyRateService;
    private final BusinessAreaService businessAreaService;
    private final NearBusService nearBusService;
    private final NearSubwayService nearSubwayService;



    // 빌딩 뉴스탭 페이지
    @GetMapping("/frame/building/news/{keyword}")
    public String search(Model model, @PathVariable("keyword") String keyword,@ModelAttribute PageRequestDTO pageRequestDTO) {
        log.info("키워드 : {}", keyword);
        log.info("페이지 : {}", pageRequestDTO.getPage());
        log.info("사이즈 : {}", pageRequestDTO.getSize());
        Page<News> findNews = newsService.search(keyword, pageRequestDTO);
        log.info("*************** BuildingController GET /findNews" + findNews);

        // 검색된 뉴스를 DTO로 변환하여 리스트에 담음
        List<News> newsDtoList = findNews.getContent().stream()
                .map(News::toDto)
                .toList();
        log.info("*************** BuildingController GET /newsDtoList" + newsDtoList);

        // 페이지 응답 DTO 생성
        PageResponseDTO pageResponseDTO = new PageResponseDTO(newsDtoList, pageRequestDTO, findNews.getTotalElements());

        // 모델에 페이지 응답 DTO와 키워드 추가
        model.addAttribute("pageDTO", pageResponseDTO);
        model.addAttribute("news", newsDtoList);
        // 뉴스 페이지로 이동
        return "detail/DetailNews";
    }

}
