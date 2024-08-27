package com.moaguide.service;


import com.moaguide.domain.elasticsearch.searchlog.SearchLog;
import com.moaguide.domain.elasticsearch.searchlog.SearchLogRepository;
import com.moaguide.dto.SearchResponseDto;
import com.moaguide.dto.searchCategoryDto;
import com.moaguide.dto.searchNewsDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SearchService {
    private final SearchLogRepository searchLogRepository;
    private final ElasticsearchOperations elasticsearchClient;

    public void savekeyword(String keyword) {
        LocalDate localDate = LocalDate.now();
        searchLogRepository.save(new SearchLog(keyword, localDate));
    }

    public SearchResponseDto searchAll(String keyword) {
        PageRequest pageRequest = PageRequest.of(0, 10);

        // building과 music 인덱스에서 검색을 동시에 수행
        Query searchQuery = NativeQuery.builder()
                .withQuery(q -> q.multiMatch(m -> m
                        .query(keyword)
                        .fields("name", "product_Id"))) // name과 product_Id 필드를 검색
                .withIndices("building", "music") // 두 인덱스를 지정
                .withPageable(pageRequest)
                .build();

        // 검색 수행: org.springframework.data.elasticsearch.core.SearchHits 사용
        SearchHits<searchCategoryDto> searchHits = elasticsearchClient.search(searchQuery, searchCategoryDto.class);

        // 뉴스 검색 쿼리 작성
        Query newsSearchQuery = NativeQuery.builder()
                .withQuery(q -> q.multiMatch(m -> m
                        .query(keyword)
                        .fields("title", "content")))  // title과 content 필드에서 검색
                .withPageable(pageRequest)
                .build();

        // 뉴스 검색 수행
        SearchHits<searchNewsDto> newsSearchHits = elasticsearchClient.search(newsSearchQuery, searchNewsDto.class);

        // 검색 결과를 리스트로 변환
        List<searchCategoryDto> categoryResults = searchHits.getSearchHits().stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());

        List<searchNewsDto> newsResults = newsSearchHits.getSearchHits().stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());

        return new SearchResponseDto(categoryResults, newsResults);
    }
}