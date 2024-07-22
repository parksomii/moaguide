package com.moaguide.controller;

import com.moaguide.domain.building.businessarea.BusinessArea;
import com.moaguide.domain.building.landregistry.LandRegistry;
import com.moaguide.domain.building.lease.Lease;
import com.moaguide.domain.building.location.Location;
import com.moaguide.domain.building.near.NearBus;
import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.NearSubwayDto;
import com.moaguide.dto.NewDto.BuildingBaseResponseDto;
import com.moaguide.dto.NewDto.BuildingSubResponseDto;
import com.moaguide.dto.TypeDto;
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
    private final SubwayTimeService subwayTimeService;
    private final SubwayWeekService subwayWeekService;
    private final PopulationService populationService;
    private final RentService rentService;
    private final VacancyRateService vacancyRateService;
    private final BuildingService buildingService;
    private final LeaseService leaseService;
    private final LocationService locationService;
    private final TransactionService transactionService;
    private final LandRegistryService landRegistryService;
    private final BusinessAreaService businessAreaService;
    private final NearSubwayService nearSubwayService;
    private final SummaryService summaryService;
    private final NearBusService nearBusService;


    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        BuildingDetail buildingDetail = buildingService.detail(product_Id);
        List<Lease> lease = leaseService.detail(product_Id);
        Location location = locationService.locate(product_Id);
        Transaction transaction = transactionService.findbyproductId(product_Id);
        LandRegistry landRegistry = landRegistryService.fingById(product_Id);
        BuildingBaseResponseDto buildingBaseResponseDto = new BuildingBaseResponseDto(buildingDetail, lease, transaction, location, landRegistry);
        return ResponseEntity.ok(buildingBaseResponseDto);
    }

    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> add(@PathVariable String product_Id) {
        String keyword = summaryService.findById(product_Id);
        if( keyword.substring(keyword.length() - 1) == "호"){
            keyword = keyword.substring(0, keyword.length() - 3);
        }
        List<TypeDto> rent = rentService.findType(product_Id);
        BusinessArea businessArea = businessAreaService.findBase(product_Id);
        List<NearSubwayDto> nearSubway = nearSubwayService.findBykeyword(keyword);
        NearBus nearBus = nearBusService.findBykeyword(keyword);
        BuildingSubResponseDto buildingSubResponseDto = new BuildingSubResponseDto(rent,businessArea,nearSubway,nearBus);
        return ResponseEntity.ok(buildingSubResponseDto);
    }

}