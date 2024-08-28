package com.moaguide.service;


import com.moaguide.domain.elasticsearch.searchlog.SearchLog;
import com.moaguide.domain.elasticsearch.searchlog.SearchLogRepository;
import com.moaguide.dto.SearchRankDto;
import com.moaguide.dto.searchCategoryDto;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {

    private final RestHighLevelClient restHighLevelClient;
    private final SearchLogRepository searchLogRepository;

    public void savekeyword(String keyword) {
        LocalDateTime localDate = LocalDateTime.now();
        searchLogRepository.save(new SearchLog(keyword, localDate));
    }

    // 검색 수행 메서드
    public List<searchCategoryDto> searchAll(String keyword) throws IOException {
        List<searchCategoryDto> results = new ArrayList<>();

        // building과 music 인덱스에서 동시에 검색
        SearchRequest searchRequest = new SearchRequest("building", "music");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 키워드로 여러 필드를 대상으로 검색, _source에서 name과 product_Id만 반환
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword)
                .field("name", 1.0f)  // 기본 가중치 1.0
                .field("address", 1.0f)
                .field("singer", 1.0f)
                .field("lyricist", 1.0f)
                .field("composer", 1.0f)
                .field("arranger", 1.0f)
                .field("description", 1.0f)
                .field("platform", 2.0f)
                .field("category", 1.0f));
        searchSourceBuilder.fetchSource(new String[]{"name", "product_Id","platform","category"}, null);  // name과 product_Id만 반환

        // 결과 개수 20개로 제한
        searchSourceBuilder.size(20);

        searchRequest.source(searchSourceBuilder);

        // 검색 요청 실행
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 디버깅: 응답 상태 및 메타데이터 출력
        System.out.println("검색 요청 완료: took = " + searchResponse.getTook() + ", 총 검색 결과: " + searchResponse.getHits().getTotalHits().value);

        // 검색 결과 처리
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            // 디버깅: 각 결과의 전체 응답 출력
            System.out.println("검색 결과 문서: " + hit.getSourceAsMap());

            // 필드 값 추출
            String productId = (String) hit.getSourceAsMap().getOrDefault("product_Id", "N/A");
            String name = (String) hit.getSourceAsMap().getOrDefault("name", "N/A");
            String platform = (String) hit.getSourceAsMap().getOrDefault("platform", "N/A");
            String category = (String) hit.getSourceAsMap().getOrDefault("category", "N/A");

            // 디버깅: 각 필드의 값 확인
            System.out.println("추출된 필드 - productId: " + productId + ", name: " + name + ", platform: " + platform + ", category: " + category);

            if (productId != null && name != null) {
                // 디버깅: 결과가 정상적으로 추가됨을 로그로 확인
                System.out.println("결과 추가 - productId: " + productId + ", name: " + name);
                searchCategoryDto result = new searchCategoryDto(productId, name, platform, category);
                results.add(result);
            } else {
                // 필드가 비어있는 경우 처리 (디버깅용)
                System.out.println("필드가 비어 있습니다: " + hit.getSourceAsMap());
            }
        }

        // 디버깅: 최종적으로 반환되는 결과 리스트 출력
        System.out.println("최종 반환되는 결과 수: " + results.size());
        return results;
    }


    // 검색어 순위 집계 메서드 (최근 24시간 기준)
    public List<SearchRankDto> getSearchRank() throws IOException {
        List<SearchRankDto> ranks = new ArrayList<>();

        // Elasticsearch에서 집계를 위한 검색 요청 생성
        SearchRequest searchRequest = new SearchRequest("search-logs");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 현재 시간에서 24시간 전으로 설정
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last24Hours = now.minusHours(24);

        // timestamp 필드에 대해 최근 24시간 범위를 설정하는 range 쿼리 추가
        searchSourceBuilder.query(QueryBuilders.rangeQuery("timestamp")
                .gte(last24Hours.toString())  // 24시간 전부터
                .lte(now.toString())          // 현재 시간까지
                .format("yyyy-MM-dd'T'HH:mm:ss")); // Elasticsearch가 인식할 수 있는 시간 형식으로 지정

        // 검색어별로 발생 빈도를 집계
        searchSourceBuilder.aggregation(
                AggregationBuilders.terms("search_terms")
                        .field("search_term.keyword")  // 검색어 집계
                        .size(10)  // 상위 10개의 검색어만 가져옴
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