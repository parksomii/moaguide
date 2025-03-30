package com.moaguide.refactor.enums;

import com.moaguide.refactor.util.EnumUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArticleCategory {
	ALL(0, "all"),
	NEWS(1, "news"),
	GUIDE(2, "guide"),
	BUILDING(3, "building"),
	ART(4, "art"),
	MUSIC(5, "music"),
	CONTENT(6, "content"),
	COW(7, "cow"),
	MONEY(8, "money");

	private final int id;
	private final String name;

	public static ArticleCategory fromName(String name) {
		return EnumUtils.findByName(ArticleCategory.class, name);
	}
}
