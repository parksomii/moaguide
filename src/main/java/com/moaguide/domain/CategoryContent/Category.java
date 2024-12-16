package com.moaguide.domain.CategoryContent;

import com.moaguide.util.EnumUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
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

    public static Category fromName(String name) {
        return EnumUtils.findByName(Category.class, name);
    }
}
