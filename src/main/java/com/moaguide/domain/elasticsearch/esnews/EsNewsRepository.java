package com.moaguide.domain.elasticsearch.esnews;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsNewsRepository extends ElasticsearchRepository<EsNews, String> {
}
