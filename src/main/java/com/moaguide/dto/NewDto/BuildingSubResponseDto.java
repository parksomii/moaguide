package com.moaguide.dto.NewDto;

import com.moaguide.domain.building.businessarea.BusinessArea;
import com.moaguide.domain.building.near.NearBus;
import com.moaguide.dto.NewDto.BuildingDto.NearSubwayDto;
import com.moaguide.dto.NewDto.BuildingDto.TypeDto;
import lombok.Getter;

import java.util.List;

@Getter
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
    private List<NearSubwayDto> nearSubway;
    private int busLine;
    private int busNode;

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
        this.nearSubway = nearSubway;
        this.busLine = businessArea.getLine();
        this.busNode = businessArea.getNode();
    }

}
