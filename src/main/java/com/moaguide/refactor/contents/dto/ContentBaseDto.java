package com.moaguide.refactor.contents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentBaseDto {

	private ContentPublishDto publish;
	private ContentInvestmentDto investment;

	public ContentBaseDto(ContentPublishDto publish) {
		this.publish = publish;
		this.investment = null;
	}
}
