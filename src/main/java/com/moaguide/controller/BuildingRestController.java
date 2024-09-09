package com.moaguide.controller;

import com.moaguide.domain.building.businessarea.BusinessArea;
import com.moaguide.domain.building.near.NearBus;
import com.moaguide.domain.building.near.NearSubway;
import com.moaguide.dto.LocationDto;
import com.moaguide.dto.NewDto.*;
import com.moaguide.dto.NewDto.BuildingDto.*;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import com.moaguide.dto.NewDto.customDto.BuildingReponseDto;
import com.moaguide.service.DivideService;
import com.moaguide.service.ProductService;
import com.moaguide.service.TransactionService;
import com.moaguide.service.building.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/building/")
public class BuildingRestController {
    private final RentService rentService;
    private final BuildingService buildingService;
    private final LeaseService leaseService;
    private final LocationService locationService;
    private final BusinessAreaService businessAreaService;
    private final NearSubwayService nearSubwayService;
    private final LandPriceService landPriceService;
    private final AreaService areaService;
    private final SubwayTimeService subwayTimeService;
    private final SubwayWeekService subwayWeekService;
    private final PopulationService populationService;
    private final VacancyRateService vacancyRateService;



    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        BuildingBaseDto building = buildingService.findbase(product_Id);
        List<LeaseDto> leaseDtos = leaseService.detail(product_Id);
        return ResponseEntity.ok(new BuildingBaseResponseDto(building,leaseDtos));
    }

    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> add(@PathVariable String product_Id) {
        List<TypeDto> rent = rentService.findType(product_Id);
        BusinessAreaDto businessArea = businessAreaService.findBase(product_Id);
        List<NearSubwayDto> nearSubway = nearSubwayService.findBykeyword(product_Id);
        BuildingSubResponseDto buildingSubResponseDto = new BuildingSubResponseDto(rent,businessArea,nearSubway);
        return ResponseEntity.ok(buildingSubResponseDto);
    }

    @GetMapping("land/{product_Id}")
    public ResponseEntity<Object> land(@PathVariable String product_Id) {
        List<LandDto> landPrice = landPriceService.priceList(product_Id);
        return ResponseEntity.ok(new BuildingLandResponseDto(landPrice));
    }

    @GetMapping("area/{product_Id}")
    public ResponseEntity<Object> area(@PathVariable String product_Id) {
        LocationDto location = locationService.locate(product_Id);
        List<AreaDto> areas = areaService.findpolygon(product_Id);
        return ResponseEntity.ok(new BuildingAreaResponseDto(location,areas));
    }

    @GetMapping("subway/{product_Id}")
    public ResponseEntity<Object> subway(@PathVariable String product_Id,@RequestParam int year,@RequestParam int month) {
        SubwayTimeDto subwayTimeDtos = subwayTimeService.findbydate(product_Id,year,month);
        List<SubwayWeekDto> subwayWeekDtos = subwayWeekService.findbydate(product_Id,year,month);
        BuildingSubwayResponseDto subwayResponseDto = new BuildingSubwayResponseDto(subwayTimeDtos,subwayWeekDtos);
        return ResponseEntity.ok(subwayResponseDto);
    }

    @GetMapping("population/{product_Id}")
    public ResponseEntity<Object> population(@PathVariable String product_Id, @RequestParam int year,@RequestParam int month) {
        List<PopulationDto> populationDto = populationService.findbydate(product_Id,year,month);
        return ResponseEntity.ok(new BuildingPopulationDto(populationDto));
    }

    @GetMapping("rate/{product_Id}")
    public ResponseEntity<Object> rate(@PathVariable String product_Id, @RequestParam String type) {
        List<RentDto> rentDtos = rentService.findBase(product_Id,type);
        List<VacancyrateDto> vacancyrateDtos = vacancyRateService.findBase(product_Id,type);
        return ResponseEntity.ok(new BuildingRateResponseDto(rentDtos,vacancyrateDtos));
    }
}