package com.moaguide.service;

import com.moaguide.domain.ArticleContent.ArticleContent;
import com.moaguide.domain.ArticleContent.ArticleContentRepository;
import com.moaguide.domain.ArticlePaywall.ArticlePaywall;
import com.moaguide.domain.ArticlePaywall.ArticlePaywallRepository;
import com.moaguide.dto.ArticlePaywallRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticlePaywallService {

    private final ArticlePaywallRepository articlePaywallRepository;
    private final ArticleContentRepository articleContentRepository;

    /**
     * 아티클 저장
     *
     * @param requestDto 아티클 요청 DTO
     */
    public void saveArticle(ArticlePaywallRequestDto requestDto) {
        // 1. contentId로 ArticleContent 조회
        ArticleContent content = articleContentRepository.findById(requestDto.getContentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 contentId에 대한 콘텐츠를 찾을 수 없습니다."));

        // 2. ArticlePaywall 객체 생성
        ArticlePaywall paywall = ArticlePaywall.builder()
                .content(content) // 연관된 ArticleContent 설정
                .paywallUp(requestDto.getPaywallUp())
                .paywallDown(requestDto.getPaywallDown())
                .build();

        // 3. 저장
        articlePaywallRepository.save(paywall);
    }

    /**
     * 아티클 삭제
     *
     * @param articleId 삭제할 아티클 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteArticleById(Long articleId) {
        // 1. articleId로 ArticlePaywall 확인
        if (!articlePaywallRepository.existsById(articleId)) {
            return false; // 삭제할 아티클이 존재하지 않음
        }

        // 2. 삭제
        articlePaywallRepository.deleteById(articleId);
        return true;
    }

}