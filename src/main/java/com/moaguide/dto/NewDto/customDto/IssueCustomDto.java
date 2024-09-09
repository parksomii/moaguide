package com.moaguide.dto.NewDto.customDto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueCustomDto {
    private String productId;
    private String name;
    private long totalprice;
    private String day;
    private String category;
    private String platform;
    private double recruitmentRate;
}
