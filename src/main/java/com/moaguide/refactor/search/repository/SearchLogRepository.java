package com.moaguide.refactor.search.repository;

import com.moaguide.refactor.search.entity.SearchLog;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"blue", "green"})
public interface SearchLogRepository extends ElasticsearchRepository<SearchLog, String> {
}
