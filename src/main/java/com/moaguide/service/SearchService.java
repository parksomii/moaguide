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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {

    private final RestHighLevelClient restHighLevelClient;
    private final SearchLogRepository searchLogRepository;

    public void savekeyword(String keyword) {
        LocalDate localDate = LocalDate.now();
        searchLogRepository.save(new SearchLog(keyword, localDate));
    }

    // 검색 수행 메서드
    public List<searchCategoryDto> searchAll(String keyword) throws IOException {
        List<searchCategoryDto> results = new ArrayList<>();

        // building과 music 인덱스에서 동시에 검색
        SearchRequest searchRequest = new SearchRequest("building", "music");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 키워드로 여러 필드를 대상으로 검색, _source에서 name과 product_Id만 반환
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "name", "address", "song", "singer", "lyricist", "composer", "arranger", "description","platform","category"));
        searchSourceBuilder.fetchSource(new String[]{"name", "product_Id","platform","category"}, null);  // name과 product_Id만 반환

        // 결과 개수 20개로 제한
        searchSourceBuilder.size(20);

        searchRequest.source(searchSourceBuilder);

        // 검색 요청 실행
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 검색 결과 처리
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            // 디버깅을 위해 전체 응답 출력
            System.out.println(hit.getSourceAsMap());

            String productId = (String) hit.getSourceAsMap().get("product_Id");
            String name = (String) hit.getSourceAsMap().get("name");
            String platform = (String) hit.getSourceAsMap().get("platform");
            String category = (String) hit.getSourceAsMap().get("category");

            if (productId != null && name != null) {
                searchCategoryDto result = new searchCategoryDto(productId, name,platform,category);
                results.add(result);
            } else {
                // 필드가 비어있는 경우 처리 (디버깅용)
                System.out.println("필드가 비어 있습니다: " + hit.getSourceAsMap());
            }
        }

        return results;
    }

    // 검색어 순위 집계 메서드
    public List<SearchRankDto> getSearchRank() throws IOException {
        List<SearchRankDto> ranks = new ArrayList<>();

        // Elasticsearch에서 집계를 위한 검색 요청 생성
        SearchRequest searchRequest = new SearchRequest("search-logs");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

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