package com.moaguide.refactor.contents.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContentTopResponseDto {

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
	private String link;
	private Boolean bookmark;

	public ContentTopResponseDto(ContentDetailDto contentDetailDto) {
		this.productId = contentDetailDto.getProductId();
		this.name = contentDetailDto.getName();
		this.genre = contentDetailDto.getGenre();
		this.category = contentDetailDto.getCategory();
		this.platform = contentDetailDto.getPlatform();
		this.totalPrice = contentDetailDto.getTotalPrice();
		this.rate = contentDetailDto.getRate();
		this.date = contentDetailDto.getDate();
		this.lowPrice = contentDetailDto.getLowPrice();
		this.Invest =
			genre.equals("MOVIE") || genre.equals("EXHIBITION") || genre.equals("CULTURE");
		this.link = contentDetailDto.getLink();
		this.bookmark = contentDetailDto.getBookmark();
	}
}
