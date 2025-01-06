package com.moaguide.service.ArticleContent;

import com.moaguide.domain.ArticleContent.ArticleView;
import com.moaguide.domain.ArticleContent.ArticleViewRepository;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleViewService {

  private final ArticleViewRepository articleViewRepository;

  @Transactional
  public void insert(Long articleId, String nickname) {
    // 현재 시간을 가져옴
    Timestamp currentTime = Timestamp.from(Instant.now());

    // ArticleView 엔티티 생성 및 저장
    ArticleView articleView = new ArticleView(nickname, articleId, currentTime);
    articleViewRepository.save(articleView);
  }
}