package com.moaguide.controller;

import com.moaguide.domain.building.businessarea.BusinessArea;
import com.moaguide.domain.building.landregistry.LandRegistry;
import com.moaguide.domain.building.lease.Lease;
import com.moaguide.domain.building.location.Location;
import com.moaguide.domain.building.near.NearBus;
import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.BuildingDetailDto;
import com.moaguide.dto.NewDto.*;
import com.moaguide.service.SummaryService;
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
    private final SummaryService summaryService;
    private final NearBusService nearBusService;
    private final LandPriceService landPriceService;
    private final AreaService areaService;
    private final SubwayTimeService subwayTimeService;
    private final SubwayWeekService subwayWeekService;
    private final PopulationService populationService;

    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        BuildingDetail buildingDetail = buildingService.detail(product_Id);
        List<Lease> lease = leaseService.detail(product_Id);
        Location location = locationService.locate(product_Id);
        Transaction transaction = transactionService.findbyproductId(product_Id);
        LandRegistry landRegistry = landRegistryService.fingById(product_Id);
        log.info("BuildingDetail: {}", buildingDetail);
        log.info("Lease: {}", lease);
        log.info("Location: {}", location);
        log.info("Transaction: {}", transaction);
        log.info("LandRegistry: {}", landRegistry);
        BuildingBaseResponseDto buildingBaseResponseDto = new BuildingBaseResponseDto(buildingDetail, lease, transaction, location, landRegistry);
        return ResponseEntity.ok(buildingBaseResponseDto);
    }

    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> add(@PathVariable String product_Id,@RequestBody String keyword) {
        List<TypeDto> rent = rentService.findType(product_Id);
        BusinessArea businessArea = businessAreaService.findBase(product_Id);
        List<NearSubwayDto> nearSubway = nearSubwayService.findBykeyword(keyword);
        NearBus nearBus = nearBusService.findBykeyword(keyword);
        BuildingSubResponseDto buildingSubResponseDto = new BuildingSubResponseDto(rent,businessArea,nearSubway,nearBus);
        return ResponseEntity.ok(buildingSubResponseDto);
    }

    @GetMapping("land/{product_Id}")
    public ResponseEntity<Object> land(@PathVariable String product_Id) {
        List<LandDto> landPrice = landPriceService.priceList(product_Id);
        return ResponseEntity.ok(new BuildingLandResponseDto(landPrice));
    }

    @GetMapping("area/{keyword}")
    public ResponseEntity<Object> area(@PathVariable String keyword) {
        List<AreaDto> areas = areaService.findpolygon(keyword);
        return ResponseEntity.ok(new BuildingAreaResponseDto(areas));
    }

    @GetMapping("subway/{keyword}")
    public ResponseEntity<Object> subway(@PathVariable String keyword,@RequestBody DayDto dayDto) {
        SubwayTimeDto subwayTimeDtos = subwayTimeService.findbydate(keyword,dayDto.getYear(),dayDto.getMonth());
        List<SubwayWeekDto> subwayWeekDtos = subwayWeekService.findbydate(keyword, dayDto.getYear(), dayDto.getMonth());
        BuildingSubwayResponseDto subwayResponseDto = new BuildingSubwayResponseDto(subwayTimeDtos,subwayWeekDtos);
        return ResponseEntity.ok(subwayResponseDto);
    }

    @GetMapping("population/{product_Id}")
    public ResponseEntity<Object> population(@PathVariable String product_Id,@RequestBody DayDto dayDto) {
        DistricIdDto districIdDto = buildingService.detailByDistricId(product_Id);
        List<PopulationDto> populationDto = populationService.findbydate(districIdDto.getDistricId(),dayDto.getYear(),dayDto.getMonth());
        return ResponseEntity.ok(new BuildingPopulationDto(populationDto));
    }

}