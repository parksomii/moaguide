package com.moaguide.service.ArticleContent;

import com.moaguide.domain.ArticleContent.ArticleContent;
import com.moaguide.domain.ArticleContent.ArticleContentRepository;
import com.moaguide.domain.user.Role;
import com.moaguide.dto.ArticleNonSubscriberDto;
import com.moaguide.dto.ArticleSubscriberDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleDetailService {

    private final ArticleContentRepository articleContentRepository;

    public Object getArticleDetail(Long articleId, String role) {
        // 아티클 조회
        ArticleContent article = articleContentRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 아티클을 찾을 수 없습니다."));

        // 카테고리 이름 가져오기
        String categoryName = article.getCategoryId().getName();

        // 유료 아티클 처리
        if (article.getIsPremium()) {
            if (Role.VIP.toString().equals(role) || Role.ADMIN.toString().equals(role)) {
                // VIP 또는 ADMIN: paywallUp + paywallDown을 합친 text 반환
                String combinedText = article.getPaywallUp() + "\n\n" + article.getPaywallDown();
                return new ArticleSubscriberDto(
                        article.getArticleId(),
                        categoryName,
                        article.getTitle(),
                        article.getAuthorName(),
                        combinedText,
                        article.getImgLink(),
                        article.getViews(),
                        article.getLikes(),
                        article.getCreatedAt().toString()
                );
            } else if (Role.USER.toString().equals(role)) {
                // USER: paywallUp만 반환
                return new ArticleNonSubscriberDto(
                        article.getArticleId(),
                        categoryName,
                        article.getTitle(),
                        article.getAuthorName(),
                        article.getPaywallUp(),
                        article.getImgLink(),
                        article.getViews(),
                        article.getLikes(),
                        article.getCreatedAt().toString()
                );
            } else {
                throw new AccessDeniedException("접근 권한이 없습니다.");
            }
        } else {
            // 무료 아티클 처리
            String combinedText = article.getPaywallUp() + "\n\n" + article.getPaywallDown();
            return new ArticleSubscriberDto(
                    article.getArticleId(),
                    categoryName,
                    article.getTitle(),
                    article.getAuthorName(),
                    combinedText,
                    article.getImgLink(),
                    article.getViews(),
                    article.getLikes(),
                    article.getCreatedAt().toString()
            );
        }
    }
}
