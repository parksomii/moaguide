package com.moaguide.controller;

import com.moaguide.domain.building.businessarea.BusinessArea;
import com.moaguide.domain.building.landregistry.LandRegistry;
import com.moaguide.domain.building.location.Location;
import com.moaguide.domain.building.near.NearBus;
import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.BuildingDetailDto;
import com.moaguide.dto.NewDto.*;
import com.moaguide.dto.NewDto.BuildingDto.*;
import com.moaguide.dto.NewDto.customDto.BuildingReponseDto;
import com.moaguide.service.DivideService;
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
    private final TransactionService transactionService;
    private final LandRegistryService landRegistryService;
    private final BusinessAreaService businessAreaService;
    private final NearSubwayService nearSubwayService;
    private final NearBusService nearBusService;
    private final LandPriceService landPriceService;
    private final AreaService areaService;
    private final SubwayTimeService subwayTimeService;
    private final SubwayWeekService subwayWeekService;
    private final PopulationService populationService;
    private final VacancyRateService vacancyRateService;
    private final DivideService divideService;

    @GetMapping("{product_Id}")
    public ResponseEntity<?> product(@PathVariable String product_Id) {
        BuildingDetail buildingDetail = buildingService.detail(product_Id);
        List<Transaction> transaction = transactionService.findtwoByproductId(product_Id);
        Divide divide = divideService.findOne(product_Id);
        return ResponseEntity.ok(new BuildingReponseDto(transaction,divide,buildingDetail.getDividendCycle()));
    }

    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        BuildingDetail buildingDetail = buildingService.detail(product_Id);
        List<LeaseDto> lease = leaseService.detail(product_Id);
        LandRegistry landRegistry = landRegistryService.fingById(product_Id);
        Divide divide = divideService.findOne(product_Id);
        BuildingBaseResponseDto buildingBaseResponseDto = new BuildingBaseResponseDto(buildingDetail.getProductId().getProductId(),new PublishDto(buildingDetail,divide.getDividend()) ,new BuildingDetailDto(buildingDetail),landRegistry,lease);
        return ResponseEntity.ok(buildingBaseResponseDto);
    }

    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> add(@PathVariable String product_Id) {
        BuildingDetail buildingDetail = buildingService.findkeyword(product_Id);
        String keyword = buildingDetail.getKeyword();
        List<TypeDto> rent = rentService.findType(keyword);
        BusinessArea businessArea = businessAreaService.findBase(product_Id);
        List<NearSubwayDto> nearSubway = nearSubwayService.findBykeyword(keyword);
        NearBus nearBus = nearBusService.findBykeyword(keyword);
        log.info("Rent Types: {}", rent);
        log.info("Business Area: {}", businessArea);
        log.info("Near Subway: {}", nearSubway);
        log.info("Near Bus: {}", nearBus);
        BuildingSubResponseDto buildingSubResponseDto = new BuildingSubResponseDto(rent,businessArea,nearSubway,nearBus);
        return ResponseEntity.ok(buildingSubResponseDto);
    }

    @GetMapping("land/{product_Id}")
    public ResponseEntity<Object> land(@PathVariable String product_Id) {
        List<LandDto> landPrice = landPriceService.priceList(product_Id);
        return ResponseEntity.ok(new BuildingLandResponseDto(landPrice));
    }

    @GetMapping("area/{product_Id}")
    public ResponseEntity<Object> area(@PathVariable String product_Id) {
        BuildingDetail buildingDetail = buildingService.findkeyword(product_Id);
        Location location = locationService.locate(buildingDetail.getProductId().getProductId());
        List<AreaDto> areas = areaService.findpolygon(buildingDetail.getKeyword());
        return ResponseEntity.ok(new BuildingAreaResponseDto(buildingDetail.getProductId().getName(),location,areas));
    }

    @GetMapping("subway/{product_Id}")
    public ResponseEntity<Object> subway(@PathVariable String product_Id,@RequestParam int year,@RequestParam int month) {
        BuildingDetail buildingDetail = buildingService.findkeyword(product_Id);
        String keyword = buildingDetail.getKeyword();
        SubwayTimeDto subwayTimeDtos = subwayTimeService.findbydate(keyword,year,month);
        List<SubwayWeekDto> subwayWeekDtos = subwayWeekService.findbydate(keyword,year,month);;
        BuildingSubwayResponseDto subwayResponseDto = new BuildingSubwayResponseDto(subwayTimeDtos,subwayWeekDtos);
        return ResponseEntity.ok(subwayResponseDto);
    }

    @GetMapping("population/{product_Id}")
    public ResponseEntity<Object> population(@PathVariable String product_Id, @RequestParam int year,@RequestParam int month) {
        IdDto idDto = buildingService.detailByDistricId(product_Id);
        List<PopulationDto> populationDto = populationService.findbydate(idDto.getDistricId(),year,month);
        return ResponseEntity.ok(new BuildingPopulationDto(populationDto));
    }

    @GetMapping("rate/{product_Id}")
    public ResponseEntity<Object> rate(@PathVariable String product_Id, @RequestParam String type) {
        BuildingDetail buildingDetail = buildingService.findkeyword(product_Id);
        String keyword = buildingDetail.getKeyword();
        List<RentDto> rentDtos = rentService.findBase(keyword,type);
        List<VacancyrateDto> vacancyrateDtos = vacancyRateService.findBase(keyword,type);
        return ResponseEntity.ok(new BuildingRateResponseDto(rentDtos,vacancyrateDtos));
    }
}