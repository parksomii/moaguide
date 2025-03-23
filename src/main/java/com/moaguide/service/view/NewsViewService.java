package com.moaguide.service.view;

import com.moaguide.refactor.news.entity.NewsView;
import com.moaguide.refactor.news.repository.NewsViewRepository;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NewsViewService {

	private final NewsViewRepository newsViewRepository;

	@Transactional
	public void insert(Long NewsId, String nickname) {
		// 현재 시간을 가져옴
		Timestamp currentTime = Timestamp.from(Instant.now());

		// ProductView 엔티티 생성 및 값 설정
		NewsView newsView = new NewsView(nickname, NewsId, currentTime);

		// JPA의 save 메서드를 사용해 엔티티 저장
		newsViewRepository.save(newsView);

	}
}
