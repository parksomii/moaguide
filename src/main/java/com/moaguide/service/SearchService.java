package com.moaguide.service;

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
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {

    private final RestHighLevelClient restHighLevelClient;
    private final SearchLogRepository searchLogRepository;
    private final ProductRepository productRepository;

    // 검색어 저장 메서드
    public void saveKeyword(String keyword) {
        searchLogRepository.save(new SearchLog(keyword, new Date()));
    }

    // 검색 수행 메서드 (product 인덱스에 대한 검색)
    public List<searchProductDto> searchProducts(String keyword) throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

// MultiMatchQuery로 검색 가능한 필드에 대해 검색, 플랫폼에 가중치 1.5배 적용
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword)
                .field("name", 1.0f)
                .field("platform", 1.5f)  // 플랫폼에 가중치 1.5배
                .field("address", 1.0f)
                .field("Director", 1.0f)
                .field("Cast", 1.0f)
                .field("Writer", 1.0f)
                .field("Singer", 1.0f)
                .field("Lyricist", 1.0f)
                .field("Composer", 1.0f)
                .field("Arranger", 1.0f)
                .field("Farm", 1.0f)
                .field("Introduction", 1.0f)).size(30);

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

        // 현재 시스템 기본 시간대를 사용한 현재 시간
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last24Hours = now.minusHours(24);

        // LocalDateTime을 Date로 변환
        Date nowDate = Date.from(Instant.from(now));
        Date last24HoursDate = Date.from(Instant.from(last24Hours));

        // timestamp 필드에 대해 최근 24시간 범위를 설정하는 range 쿼리 추가
        searchSourceBuilder.query(QueryBuilders.rangeQuery("timestamp")
                .gte(last24HoursDate.getTime())
                .lte(nowDate.getTime()));

        // 검색어별로 발생 빈도를 집계
        searchSourceBuilder.aggregation(
                AggregationBuilders.terms("search_terms")
                        .field("searchTerm.keyword")  // 검색어 집계 (필드명 수정)
                        .size(5)  // 상위 5개의 검색어만 가져옴
        );

        // searchSourceBuilder를 searchRequest에 설정
        searchRequest.source(searchSourceBuilder);

        // 검색 요청 실행
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // Elasticsearch 응답에서 집계 결과를 안전하게 추출
        if (searchResponse.getAggregations() != null) {
            Terms searchTermsAgg = searchResponse.getAggregations().get("search_terms");
            if (searchTermsAgg != null) {
                int rank = 1;
                for (Terms.Bucket bucket : searchTermsAgg.getBuckets()) {
                    SearchRankDto searchRankDto = new SearchRankDto();
                    searchRankDto.setKeyword(bucket.getKeyAsString());
                    searchRankDto.setRank(rank++);
                    ranks.add(searchRankDto);
                }
            } else {
                // search_terms 집계가 없는 경우
                System.out.println("No aggregation found for 'search_terms'.");
            }
        } else {
            // Aggregations가 null인 경우
            System.out.println("No aggregations found in the search response.");
        }

        return ranks;
    }

}
