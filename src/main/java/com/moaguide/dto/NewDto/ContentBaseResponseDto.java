package com.moaguide.dto.NewDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentBaseResponseDto {
    private String productId;
    private String name;
    private String genre;
    private String category;
    private String platform;
    private long totalPrice;
    private Double rate;
    private Date date;
    private Integer lowPrice;
    private boolean Invest;

    public ContentBaseResponseDto(ContentDetailDto contentDetailDto) {
        this.productId = contentDetailDto.getProductId();
        this.name = contentDetailDto.getName();
        this.genre = contentDetailDto.getGenre();;
        this.category = contentDetailDto.getCategory();
        this.platform = contentDetailDto.getPlatform();
        this.totalPrice = contentDetailDto.getTotalPrice();
        this.rate = contentDetailDto.getRate();
        this.date = contentDetailDto.getDate();
        this.lowPrice = contentDetailDto.getLowPrice();
        if(genre.equals("MOVIE") || genre.equals("EXHIBITION") || genre.equals("CULTURE")){
            this.Invest = true;
        }else{
            this.Invest = false;
        }
    }
}
