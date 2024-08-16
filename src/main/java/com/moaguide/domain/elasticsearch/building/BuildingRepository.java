package com.moaguide.domain.elasticsearch.building;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BuildingRepository extends ElasticsearchRepository<Building, String> {
}
