package com.moaguide.controller;


import com.moaguide.dto.LocationDto;
import com.moaguide.dto.NewDto.*;
import com.moaguide.dto.NewDto.BuildingDto.*;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import com.moaguide.dto.NewDto.customDto.BuildingReponseDto;
import com.moaguide.dto.NewDto.customDto.BuildingTypeResponseDto;
import com.moaguide.jwt.JWTUtil;
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
@RequestMapping("/detail/building")
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
    private final LandRegistryService landRegistryService;
    private final JWTUtil jwtUtil;


//    @GetMapping("/{product_Id}")
//    public ResponseEntity<?> product(@PathVariable String product_Id, @RequestHeader("Authorization") String auth) {
//        String Nickname = jwtUtil.getNickname(auth.substring(7));
//        BuildingReponseDto building = buildingService.findBydetail(product_Id,Nickname);
//        return ResponseEntity.ok(building);
//    }

    @GetMapping("/{product_Id}")
    public ResponseEntity<?> product(@PathVariable String product_Id) {
        String Nickname = "moaguide";
        List<TypeDto> rent = rentService.findType(product_Id);
        BuildingReponseDto building = buildingService.findBydetail(product_Id,Nickname);
        if(!rent.isEmpty() && !rent.equals(null) && rent.get(0).equals("오피스")) {
            building.setRentTpye(Boolean.TRUE);
            return ResponseEntity.ok(building);
        }else {
            building.setRentTpye(Boolean.FALSE);
            return ResponseEntity.ok(building);
        }
    }

    @GetMapping("/base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        BuildingBaseDto building = landRegistryService.findbase(product_Id);
        List<LeaseDto> leaseDtos = leaseService.detail(product_Id);
        return ResponseEntity.ok(new BuildingBaseResponseDto(building,leaseDtos));
    }

        @GetMapping("/sub/{product_Id}")
    public ResponseEntity<Object> add(@PathVariable String product_Id) {
        BusinessAreaDto businessArea = businessAreaService.findBase(product_Id);
        List<NearSubwayDto> nearSubway = nearSubwayService.findBykeyword(product_Id);
        BuildingSubResponseDto buildingSubResponseDto = new BuildingSubResponseDto(businessArea,nearSubway);
        return ResponseEntity.ok(buildingSubResponseDto);
    }

    @GetMapping("/land/{product_Id}")
    public ResponseEntity<Object> land(@PathVariable String product_Id) {
        List<LandDto> landPrice = landPriceService.priceList(product_Id);
        Map<String, Object> response = new HashMap<>();
        response.put("lands", landPrice);
        return ResponseEntity.ok(response   );
    }

    @GetMapping("/area/{product_Id}")
    public ResponseEntity<Object> area(@PathVariable String product_Id) {
        LocationDto location = locationService.locate(product_Id);
        List<AreaDto> areas = areaService.findpolygon(product_Id);
        return ResponseEntity.ok(new BuildingAreaResponseDto(location,areas));
    }

    @GetMapping("/subway/{product_Id}")
    public ResponseEntity<Object> subway(@PathVariable String product_Id,@RequestParam int year,@RequestParam int month) {
        SubwayTimeDto subwayTimeDtos = subwayTimeService.findbydate(product_Id,year,month);
        List<SubwayWeekDto> subwayWeekDtos = subwayWeekService.findbydate(product_Id,year,month);
        BuildingSubwayResponseDto subwayResponseDto = new BuildingSubwayResponseDto(subwayTimeDtos,subwayWeekDtos);
        return ResponseEntity.ok(subwayResponseDto);
    }

    @GetMapping("/population/{product_Id}")
    public ResponseEntity<Object> population(@PathVariable String product_Id, @RequestParam int year,@RequestParam int month) {
        List<PopulationDto> populationDto = populationService.findbydate(product_Id,year,month);
        Map<String, Object> response = new HashMap<>();
        response.put("populations", populationDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rentrate/{product_Id}")
    public ResponseEntity<Object> rentrate(@PathVariable String product_Id, @RequestParam String type,@RequestParam int syear,@RequestParam int eyear) {
        List<RentDto> rentDtos = rentService.findBase(product_Id,type,syear,eyear);
        Map<String, Object> response = new HashMap<>();
        response.put("rent", rentDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vacancyrate/{product_Id}")
    public ResponseEntity<Object> vacancyrate(@PathVariable String product_Id, @RequestParam String type,@RequestParam int syear,@RequestParam int eyear) {
        List<VacancyrateDto> vacancyrateDtos = vacancyRateService.findBase(product_Id,type,syear,eyear);
        Map<String, Object> response = new HashMap<>();
        response.put("vacancyrate", vacancyrateDtos);
        return ResponseEntity.ok(response);
    }
}