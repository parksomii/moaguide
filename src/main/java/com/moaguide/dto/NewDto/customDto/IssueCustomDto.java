package com.moaguide.dto.NewDto.customDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
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
}
