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

    public List<searchCategoryDto> searchAll(String keyword) throws IOException {
        List<searchCategoryDto> results = new ArrayList<>();

        // building과 music 인덱스에서 동시에 검색 쿼리 생성
        SearchRequest searchRequest = new SearchRequest("building", "music");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "name", "product_Id"));
        searchRequest.source(searchSourceBuilder);

        // 검색 수행
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            // 검색 결과를 searchCategoryDto로 변환 (필요에 따라 변환 로직 추가)
            searchCategoryDto result = new searchCategoryDto(
                    (String) hit.getSourceAsMap().get("product_Id"),
                    (String) hit.getSourceAsMap().get("name")
            );
            results.add(result);
        }

        return results;
    }
}