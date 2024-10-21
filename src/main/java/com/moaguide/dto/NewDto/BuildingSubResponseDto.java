package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.BuildingDto.NearBusDto;
import com.moaguide.dto.NewDto.BuildingDto.NearSubwayDto;
import com.moaguide.dto.NewDto.BuildingDto.NearSubwayListDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BuildingSubResponseDto {
    private String cbd;
    private String cbdDistance;
    private String cbdCar;
    private String cbdSubway;
    private String gbd;
    private String gbdDistance;
    private String gbdCar;
    private String gbdSubway;
    private String ybd;
    private String ybdDistance;
    private String ybdCar;
    private String ybdSubway;
    private List<NearSubwayListDto> nearSubway;
    private String node;
    private String line;


    public BuildingSubResponseDto(BusinessAreaDto businessArea, List<NearSubwayDto> nearSubway) {
        this.cbd = businessArea.getCbd();
        this.cbdDistance = businessArea.getCbdDistance();
        this.cbdCar = businessArea.getCbdCar();
        this.cbdSubway = businessArea.getCbdSubway();
        this.gbd = businessArea.getGbd();
        this.gbdDistance = businessArea.getGbdDistance();
        this.gbdCar = businessArea.getGbdCar();
        this.gbdSubway = businessArea.getGbdSubway();
        this.ybd = businessArea.getYbd();
        this.ybdDistance = businessArea.getYbdDistance();
        this.ybdCar = businessArea.getYbdCar();
        this.ybdSubway = businessArea.getYbdSubway();
        // nearSubway 리스트를 초기화
        this.nearSubway = new ArrayList<>();

        // nearSubway 리스트에 NearSubwayListDto 객체를 추가
        for(NearSubwayDto near : nearSubway) {
            this.nearSubway.add(new NearSubwayListDto(near));
        }
        this.node = businessArea.getNode();
        this.line = businessArea.getLine();
    }

}
