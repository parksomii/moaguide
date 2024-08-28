package com.moaguide.service;

import com.moaguide.domain.elasticsearch.product.ProductEntity;
import com.moaguide.domain.elasticsearch.product.ProductRepository;
import com.moaguide.domain.elasticsearch.searchlog.SearchLog;
import com.moaguide.domain.elasticsearch.searchlog.SearchLogRepository;
import com.moaguide.dto.SearchRankDto;
import com.moaguide.dto.searchProductDto;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {

    private final RestHighLevelClient restHighLevelClient;
    private final SearchLogRepository searchLogRepository;
    private final ProductRepository productRepository;

    // 검색어 저장 메서드
    public void saveKeyword(String keyword) {
        LocalDateTime localDate = LocalDateTime.now().withNano(0); // 나노초 제거
        searchLogRepository.save(new SearchLog(keyword, localDate));
    }

    // 검색 수행 메서드 (product 인덱스에 대한 검색)
    public List<searchProductDto> searchProducts(String keyword) throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // MultiMatchQuery로 여러 필드에서 검색, 플랫폼에 가중치 1.5배 적용
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword)
                .field("name", 1.0f)
                .field("platform", 1.5f)  // 플랫폼에 가중치 1.5배
                .field("address", 1.0f)
                .field("singer", 1.0f)
                .field("lyricist", 1.0f)
                .field("composer", 1.0f)
                .field("arranger", 1.0f)
                .field("introduction", 1.0f)).size(30);

        // 필요한 필드만 반환
        searchSourceBuilder.fetchSource(new String[]{"name", "product_Id", "platform", "category"}, null);

        searchRequest.source(searchSourceBuilder);

        // 검색 요청 실행
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 검색 결과 처리
        List<searchProductDto> results = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            // 필드 값 추출 및 DTO 생성
            searchProductDto productDto = new searchProductDto(
                    (String) hit.getSourceAsMap().getOrDefault("product_Id", "N/A"),
                    (String) hit.getSourceAsMap().getOrDefault("name", "N/A"),
                    (String) hit.getSourceAsMap().getOrDefault("platform", "N/A"),
                    (String) hit.getSourceAsMap().getOrDefault("category", "N/A")
            );

            results.add(productDto);
        }

        return results;
    }

    // 검색어 순위 집계 메서드 (최근 24시간 기준)
    public List<SearchRankDto> getSearchRank() throws IOException {
        List<SearchRankDto> ranks = new ArrayList<>();

        // Elasticsearch에서 집계를 위한 검색 요청 생성
        SearchRequest searchRequest = new SearchRequest("search-logs");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 현재 시간에서 24시간 전으로 설정 (ZonedDateTime을 사용하여 시간대 정보 포함)
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime last24Hours = now.minusHours(24);

        // timestamp 필드에 대해 최근 24시간 범위를 설정하는 range 쿼리 추가
        searchSourceBuilder.query(QueryBuilders.rangeQuery("timestamp")
                .gte(last24Hours.format(DateTimeFormatter.ISO_DATE_TIME))  // 24시간 전부터
                .lte(now.format(DateTimeFormatter.ISO_DATE_TIME)));        // 현재 시간까지

        // 검색어별로 발생 빈도를 집계
        searchSourceBuilder.aggregation(
                AggregationBuilders.terms("search_terms")
                        .field("search_term.keyword")  // 검색어 집계
                        .size(5)  // 상위 5개의 검색어만 가져옴
        );

        searchRequest.source(searchSourceBuilder);

        // 검색 요청 실행
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 집계 결과에서 검색어와 빈도 추출
        Terms searchTermsAgg = searchResponse.getAggregations().get("search_terms");
        int rank = 1;
        for (Terms.Bucket bucket : searchTermsAgg.getBuckets()) {
            SearchRankDto searchRankDto = new SearchRankDto();
            searchRankDto.setKeyword(bucket.getKeyAsString());
            searchRankDto.setRank(rank++);
            ranks.add(searchRankDto);
        }

        return ranks;
    }
}
