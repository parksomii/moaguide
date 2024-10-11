package com.moaguide.dto.NewDto.musicDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultDto {
    private Map<String, List<SearchDto>> search = new HashMap<>();  // keyword별 검색 결과를 담는 Map

    public void addSearchResult(String keyword, SearchDto searchDto) {
        search.computeIfAbsent(keyword, k -> new ArrayList<>()).add(searchDto);
    }
}
