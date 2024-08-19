package com.moaguide.domain.elasticsearch.music;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MusicRepository extends ElasticsearchRepository<Music, String> {
}
