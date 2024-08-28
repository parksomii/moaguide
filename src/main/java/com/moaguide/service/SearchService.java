package com.moaguide.service;


import com.moaguide.domain.elasticsearch.searchlog.SearchLog;
import com.moaguide.domain.elasticsearch.searchlog.SearchLogRepository;
import com.moaguide.dto.searchCategoryDto;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
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
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "name", "address", "song", "singer", "lyricist", "composer", "arranger", "description"));
        searchSourceBuilder.fetchSource(new String[]{"name", "product_Id"}, null);  // name과 product_Id만 반환

        searchRequest.source(searchSourceBuilder);

        // 검색 요청 실행
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 검색 결과 처리
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            // 디버깅을 위해 전체 응답 출력
            System.out.println(hit.getSourceAsMap());

            String productId = (String) hit.getSourceAsMap().get("product_Id");
            String name = (String) hit.getSourceAsMap().get("name");

            if (productId != null && name != null) {
                searchCategoryDto result = new searchCategoryDto(productId, name);
                results.add(result);
            } else {
                // 필드가 비어있는 경우 처리 (디버깅용)
                System.out.println("필드가 비어 있습니다: " + hit.getSourceAsMap());
            }
        }

        return results;
    }
}