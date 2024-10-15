package com.moaguide.dto.NewDto.BuildingDto;

import lombok.Getter;

import java.util.List;

@Getter

public class NearSubwayListDto {
    private String station;

    private List<String> route;

    private Integer distance;

    private Integer time;
    public NearSubwayListDto(NearSubwayDto nearSubway) {
        this.station = nearSubway.getStation();
        this.distance = nearSubway.getDistance();
        this.time = nearSubway.getTime();
        this.route = nearSubway.getRoutes();
    }

}
