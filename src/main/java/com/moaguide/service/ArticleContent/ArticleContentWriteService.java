package com.moaguide.service.ArticleContent;

import com.moaguide.domain.ArticleContent.ArticleContent;
import com.moaguide.domain.ArticleContent.ArticleContentRepository;
import com.moaguide.domain.CategoryContent.CategoryContent;
import com.moaguide.domain.CategoryContent.CategoryContentRepository;
import com.moaguide.dto.ArticleContentWriteRequestDto;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleContentWriteService {

  private final ArticleContentRepository articleContentRepository;
  private final CategoryContentRepository categoryContentRepository;

  /**
   * 아티클 저장
   *
   * @param requestDto 아티클 요청 DTO
   */
  public void saveArticle(ArticleContentWriteRequestDto requestDto) {
    // 1. CategoryContent 조회
    CategoryContent categoryContent = categoryContentRepository.findById(requestDto.getCategoryId())
        .orElseThrow(() -> new IllegalArgumentException("해당 categoryId에 대한 카테고리를 찾을 수 없습니다."));

    // 2. ArticleContent 생성
    ArticleContent articleContent = ArticleContent.builder()
        .title(requestDto.getTitle())
        .authorName(requestDto.getAuthorName())
        .categoryId(categoryContent)
        .type(requestDto.getType())
        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
        .isPremium(requestDto.isPremium())
        .views(0) // 초기 조회수 설정
        .imgLink(requestDto.getImageLink())
        .paywallUp(requestDto.getPaywallUp())
        .paywallDown(requestDto.getPaywallDown())
        .build();

    // 3. 저장
    articleContentRepository.save(articleContent);
  }

  /**
   * 아티클 삭제
   *
   * @param articleId 삭제할 아티클 ID
   * @return 삭제 성공 여부
   */
  public boolean deleteArticleById(Long articleId) {
    // 1. articleId로 ArticlePaywall 확인
    if (!articleContentRepository.existsById(articleId)) {
      return false; // 삭제할 아티클이 존재하지 않음
    }

    // 2. 삭제
    articleContentRepository.deleteById(articleId);
    return true;
  }

}