package com.moaguide.service;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
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

@Service
@AllArgsConstructor
public class SearchService {
    private final SearchLogRepository searchLogRepository;
    private ElasticsearchOperations elasticsearchOperations;


    public void savekeyword(String keyword) {
        LocalDate localDate = LocalDate.now();
        searchLogRepository.save(new SearchLog(keyword,localDate));
    }

    public SearchResponseDto searchAll(String keyword) {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Query searchQuery = NativeQuery.builder()
                .withQuery(q -> q.multiMatch(m -> m
                        .query(keyword)
                        .fields("name", "product_Id")))
                .withPageable(pageRequest)
                .build();

        // 검색 수행: org.springframework.data.elasticsearch.core.SearchHits 사용
        SearchHits<searchCategoryDto> searchHits = elasticsearchOperations.search(searchQuery, searchCategoryDto.class);

        // 함수형 인터페이스로 multiMatchQuery 작성
        Query NewssearchQuery = NativeQuery.builder()
                .withQuery(q -> q.multiMatch(m -> m
                        .query(keyword)
                        .fields("title", "content")))  // title과 content 필드에서 검색
                .withPageable(pageRequest)
                .build();

        // 검색 수행
        SearchHits<searchNewsDto> newssearchHits = elasticsearchOperations.search(searchQuery, searchNewsDto.class);

        // 검색 결과를 리스트로 변환 및 병합
        List<searchCategoryDto> categoryResults = searchHits.getSearchHits().stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());

        List<searchNewsDto> newsResults = newssearchHits.getSearchHits().stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());

        return new SearchResponseDto(categoryResults,newsResults);
    }
}
