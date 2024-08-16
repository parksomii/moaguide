package com.moaguide.domain.elasticsearch.searchlog;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchLogRepository extends ElasticsearchRepository<SearchLog, String> {
}
