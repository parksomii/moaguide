package com.moaguide.service;

import com.moaguide.domain.news.News;
import com.moaguide.domain.news.NewsRepository;
import com.moaguide.dto.PageRequestDTO;
import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class NewsService {
    private final NewsRepository newsRepository;

    // 최신 뉴스 조회
    public List<NewsCustomDto> getMainNews(Pageable pageable) {
        Page<News> newsList = newsRepository.findLatest(pageable);
        List<NewsCustomDto> newsData = newsList.
                stream().
                map(news -> new NewsCustomDto(news))
                .collect(Collectors.toList());
        return newsData;
    }

    // 뉴스 최신순 전체 조회
    public Page<News> getAllByLatest(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("date").descending());
        Page<News> findNewsByLatest = newsRepository.findAll(pageable);
        log.info("NewsService findAllByLatest - result : {}", findNewsByLatest);
        log.info("NewsService findAllByLatest - result.getContent() : {}", findNewsByLatest.getContent());
        return findNewsByLatest;
    }

    // 뉴스 조회수 순 전체 조회
    public Page<News> getAllByViews(PageRequestDTO pageRequestDTO) {
/*        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("views").descending());*/
        // 1페이지 3개만 보이기
        Pageable pageable = PageRequest.of(0, 3, Sort.by("views").descending());
        Page<News> findNewsByViews = newsRepository.findAll(pageable);
        log.info("NewsService findAllByViews - result : {}", findNewsByViews);
        log.info("NewsService findAllByViews - result.getContent() : {}", findNewsByViews.getContent());
        return findNewsByViews;
    }


    public List<News> searchNews(String keyword) {
        List<News> newsList = newsRepository.findAll();
        return newsList;
    }

    public Page<News> search(String keyword, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("id").descending());
        Page<News> result = newsRepository.findByKeyword(keyword, pageable);
        log.info("result : {}", result);
        log.info("result.getContent() : {}", result.getContent());
        return result;
    }

    // 조회수 올리는 메소드
    @Transactional
    public void incrementViewCount(Long id) {
        newsRepository.updateViewCount(id);
    }

    public String getNewsLink(Long id) {
        return newsRepository.getNewsLink(id);
    }

    public List<News> findAllBylast() {
        Pageable pageable = PageRequest.of(0,2);
        return newsRepository.findTop2ByOrderByDateDesc(pageable);
    }

    public Page<NewsCustomDto> findBydetail(String keyword, Pageable pageable) {
        Page<NewsCustomDto> newsCustomDtos = newsRepository.findBydetail(keyword,pageable);
        return newsCustomDtos;
    }

    public News findById(String newsId) {
        return newsRepository.findById(newsId);
    }
}
