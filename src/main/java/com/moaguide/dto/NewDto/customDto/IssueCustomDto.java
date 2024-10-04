package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueCustomDto {
    private String productId;
    private String name;
    private long totalprice;
    private Date day;
    private String category;
    private String platform;
    private double recruitmentRate;
    private Boolean bookmark;
}
