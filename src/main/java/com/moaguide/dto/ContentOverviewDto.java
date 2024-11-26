package com.moaguide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentOverviewDto {

    private int contentId;
    private String title;
    private String type;
    private boolean isPremium;
    private String description;
    private String img_link;
    private String categoryName;
}
