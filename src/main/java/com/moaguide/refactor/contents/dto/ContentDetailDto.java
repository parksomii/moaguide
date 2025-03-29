package com.moaguide.refactor.contents.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ContentDetailDto {

	private String productId;
	private String name;
	private String genre;
	private String category;
	private String platform;
	private long totalPrice;
	private Double rate;
	private Date date;
	private Integer lowPrice;
	private String link;
	private Boolean bookmark;
}
