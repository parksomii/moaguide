package com.moaguide.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContentDto {
    private int contentId;
    private String title;
    private String type;
    private boolean isPremium;
    private int views;
    private Date date;
    private int likes;
    private String description;
    private String img_link;
}
