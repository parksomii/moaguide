package com.moaguide.dto.NewDto;

import com.moaguide.dto.NewDto.customDto.ArticleDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor
@Data
public class ArticlelistResponseDto {
    private List<ArticleDto> articleList;
    private Long nextCursor; // id는 Long 타입으로 가정

    public ArticlelistResponseDto(Page<ArticleDto> article) {
        this.articleList = article.getContent();

        // 10번째 요소의 id를 nextCursur에 할당, 없으면 null 처리
        if (articleList.size() >= 10) {
            this.nextCursor = articleList.get(9).getId(); // 10번째 요소 (index 9)
        } else {
            this.nextCursor = null; // 10번째 요소가 없으면 null 처리
        }
    }
}
