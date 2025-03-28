package com.moaguide.refactor.search.dto;

import lombok.Getter;


@Getter
public class SearchLogDto {

	private String searchTerm;
	private Long docCount;

	public SearchLogDto(String searchTerm, Long docCount) {
		this.searchTerm = searchTerm;
		this.docCount = docCount;
	}

	// Getter와 Setter
	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public Long getDocCount() {
		return docCount;
	}

	public void setDocCount(Long docCount) {
		this.docCount = docCount;
	}
}
