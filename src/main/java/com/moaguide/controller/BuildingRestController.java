package com.moaguide.controller;


import com.moaguide.dto.LocationDto;
import com.moaguide.dto.NewDto.*;
import com.moaguide.dto.NewDto.BuildingDto.*;
import com.moaguide.dto.NewDto.customDto.BuildingBaseDto;
import com.moaguide.dto.NewDto.customDto.BuildingReponseDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.building.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final SubwayService subwayService;
    private final PopulationService populationService;
    private final VacancyRateService vacancyRateService;
    private final LandRegistryService landRegistryService;
    private final JWTUtil jwtUtil;


    @GetMapping("/{product_Id}")
    public ResponseEntity<?> product(@PathVariable String product_Id, @RequestHeader(value="Authorization",required = false) String jwt) {
        String Nickname;
        if ( jwt!= null && jwt.startsWith("Bearer ")) {
            if(jwtUtil.isExpired(jwt.substring(7))){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Nickname = jwtUtil.getNickname(jwt.substring(7));
        }else{
            Nickname = "null";
        }
        BuildingReponseDto building = buildingService.findBydetail(product_Id,Nickname);
        return ResponseEntity.ok(building);
    }

    @GetMapping("/base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        BuildingBaseDto building = landRegistryService.findbase(product_Id);
        List<LeaseDto> leaseDtos = leaseService.detail(product_Id);
        // null 처리
        if(building == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        // 빈 리스트 처리
        if(leaseDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content available.");
        }
        return ResponseEntity.ok(new BuildingBaseResponseDto(building,leaseDtos));
    }

        @GetMapping("/sub/{product_Id}")
    public ResponseEntity<Object> add(@PathVariable String product_Id) {
        BusinessAreaDto businessArea = businessAreaService.findBase(product_Id);
        List<NearSubwayDto> nearSubway = nearSubwayService.findBykeyword(product_Id);
        // null 처리
        if(businessArea == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        // 빈 리스트 처리
        if(nearSubway.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content available.");
        }
        BuildingSubResponseDto buildingSubResponseDto = new BuildingSubResponseDto(businessArea,nearSubway);
                return ResponseEntity.ok(buildingSubResponseDto);
    }

    @GetMapping("/land/{product_Id}")
    public ResponseEntity<Object> land(@PathVariable String product_Id) {
        List<LandDto> landPrice = landPriceService.priceList(product_Id);
        // null 처리
        if (landPrice == null) {
            return ResponseEntity.ok(new HashMap<>());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("lands", landPrice);
        return ResponseEntity.ok(response   );
    }

    @GetMapping("/area/{product_Id}")
    public ResponseEntity<Object> area(@PathVariable String product_Id) {
        LocationDto location = locationService.locate(product_Id);
        List<AreaDto> areas = areaService.findpolygon(product_Id);
        // null 처리
        if(location == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        return ResponseEntity.ok(new BuildingAreaResponseDto(location,areas));
    }

    @GetMapping("/subway/{productId}")
    public ResponseEntity<Object> subway(@PathVariable String productId) {
        BuildingSubwayResponseDto subwayResponseDto= subwayService.findByProductId(productId);
        // null 처리
        if(subwayResponseDto == null || subwayResponseDto.getSubwayDay().isEmpty() || subwayResponseDto.getSubwayMonth().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        return ResponseEntity.ok(subwayResponseDto);
    }

    @GetMapping("/population/{product_Id}")
    public ResponseEntity<Object> population(@PathVariable String product_Id, @RequestParam int year,@RequestParam int month) {
        List<PopulationDto> populationDto = populationService.findbydate(product_Id,year,month);
        // null 처리
        if (populationDto == null) {
            return ResponseEntity.ok(new HashMap<>());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("populations", populationDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rentrate/{product_Id}")
    public ResponseEntity<Object> rentrate(@PathVariable String product_Id, @RequestParam String type,@RequestParam int syear,@RequestParam int eyear) {
        Map<String, List<RentDto>> rentDtos = rentService.getRentByRegion(product_Id,type,syear,eyear);
        // null 처리
        if (rentDtos == null) {
            return ResponseEntity.ok(new HashMap<>());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("rent", rentDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vacancyrate/{product_Id}")
    public ResponseEntity<Object> vacancyrate(@PathVariable String product_Id, @RequestParam String type,@RequestParam int syear,@RequestParam int eyear) {
        Map<String,List<VacancyrateDto>> vacancyrateDtos = vacancyRateService.findBase(product_Id,type,syear,eyear);
        // null 처리
        if (vacancyrateDtos == null) {
            return ResponseEntity.ok(new HashMap<>());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("vacancyrate", vacancyrateDtos);
        return ResponseEntity.ok(response);
    }
}