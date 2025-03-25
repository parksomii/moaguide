package com.moaguide.refactor.art.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtBaseResponseDto {

	private ArtPublishDto artPublish;
	private ArtAuthorDto artAuthor;
	private ArtWorkDto artWork;
}
