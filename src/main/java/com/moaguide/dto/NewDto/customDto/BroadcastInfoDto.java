package com.moaguide.dto.NewDto.customDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BroadcastInfoDto {

    private Date airDate;
    private String director;
    private String cast;
    private String company;
    private String channel;
}
