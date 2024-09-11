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
    private int page;
    private int size;
    private int total;

    public ArticlelistResponseDto(Page<ArticleDto> article) {
        this.articleList = article.getContent();
        this.page = article.getNumber();
        this.size = article.getSize();
        this.total = article.getTotalPages();
    }
}
