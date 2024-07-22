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

    // 아티클 준비안됨 TODO: 아티클 준비되면 변경
    @GetMapping("/frame/building/ready")
    public String ready() {
        return "ready";
    }

    //
    @GetMapping("/frame/building/add/{id}")
    public String addPage(Model model, @PathVariable String id) {
        Summary summary = summaryService.findById(id);
        if(summary.getPlatformId().getPlatformId() % 2 == 0){
            return "saleComplet";
        }
        else {
            LocalDate today = LocalDate.now();
            LocalDate targetDate;
            if (today.getDayOfMonth() >= 7) {
                // 오늘 날짜가 7일 이상이면 전달로 설정합니다.
                targetDate = today.minusMonths(1);
            } else {
                // 오늘 날짜가 7일 미만이면 전전달로 설정합니다.
                targetDate = today.minusMonths(2);
            }
            BuildingDetailDto buildingDetailDto = buildingService.detail(id);
            if(buildingDetailDto.getDistrictsId() != null){
                List<Population> population = populationService.findBase(buildingDetailDto.getDistrictsId().getDistrictsId(), targetDate);
                model.addAttribute("populations", population);
            }else {
                model.addAttribute("populations", null);
            }
            SubwayTime subwayTime = subwayTimeService.findBase(buildingDetailDto.getKeyword(), targetDate);
            List<SubwayWeek> subwayWeek = subwayWeekService.findBase(buildingDetailDto.getKeyword(), targetDate);
            List<TypeDto> type = rentService.findType(buildingDetailDto.getKeyword());
            if (type.isEmpty()) {
                model.addAttribute("rent", null);
                model.addAttribute("vacancyRate", null);
            } else {
                List<Rent> rent = rentService.findBase(buildingDetailDto.getKeyword(), type.get(0).getType());
                List<VacancyRate> vacancyRate = vacancyRateService.findBase(buildingDetailDto.getKeyword(), type.get(0).getType());
                model.addAttribute("rent", rent);
                model.addAttribute("vacancyRate", vacancyRate);
            }
            BusinessArea businessArea = businessAreaService.findBase(id);
            NearBus nearBus = nearBusService.findBykeyword(buildingDetailDto.getKeyword());
            log.info("NearBus: {}", nearBus);
            List<NearSubway> nearSubway = nearSubwayService.findBykeyword(buildingDetailDto.getKeyword());
            model.addAttribute("building", buildingDetailDto);
            model.addAttribute("subwayTime", subwayTime);
            model.addAttribute("subwayWeek", subwayWeek);
            model.addAttribute("businessArea", businessArea);
            model.addAttribute("nearBus", nearBus);
            model.addAttribute("nearSubway", nearSubway);
            model.addAttribute("Type", type);
            return "Building/BuildingAdditional";
        }
    }

}
