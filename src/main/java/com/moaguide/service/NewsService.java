package com.moaguide.service;

import com.moaguide.domain.news.News;
import com.moaguide.domain.news.NewsRepository;
import com.moaguide.dto.NewDto.customDto.NewsCustomDto;
import com.moaguide.dto.PageRequestDTO;
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
    private final  NewsRepository newsRepository;
    // 많이 본 뉴스
    public List<NewsCustomDto> findNews() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("views"), Sort.Order.desc("id")));
        return newsRepository.findTop3ByOrderByViewsDesc(pageable)
                .stream()
                .map(NewsCustomDto::new)
                .collect(Collectors.toList());
    }

    // 메인 최신 뉴스 조회
    public List<NewsCustomDto> getMainNews(Pageable pageable) {
        Page<News> newsList = newsRepository.findLatest(pageable);
        List<NewsCustomDto> newsData = newsList.
                stream().
                map(news -> new NewsCustomDto(news))
                .collect(Collectors.toList());
        return newsData;
    }

    // 뉴스 최신순 전체 조회
    public Page<NewsCustomDto> getAllByLatest(int page, int size, String category) {
        if (category.equals("all")) {
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("id"),Sort.Order.desc("date")));
            Page<News> newsData = newsRepository.findAll(pageable);
            Page<NewsCustomDto> findNewsByLatest = newsData.map(news -> new NewsCustomDto(news));
            return findNewsByLatest;
        }
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("id"),Sort.Order.desc("date")));
        Page<News> newsData = newsRepository.findAllByCategory(pageable, category);
        Page<NewsCustomDto> findNewsByLatest = newsData.map(news -> new NewsCustomDto(news));
        return findNewsByLatest;
    }

    // 뉴스 인기순 전체 조회
    public Page<NewsCustomDto> getAllByViews(int page, int size, String category) {
        if (category.equals("all")) {
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("views"), Sort.Order.desc("id")));
            Page<News> newsData = newsRepository.findAll(pageable);
            Page<NewsCustomDto> findNewsByViews = newsData.map(news -> new NewsCustomDto(news));
            return findNewsByViews;
        }
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("views"), Sort.Order.desc("id")));
        Page<News> newsData = newsRepository.findAllByCategory(pageable, category);
        Page<NewsCustomDto> findNewsByViews = newsData.map(news -> new NewsCustomDto(news));
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

    public String getNewsLink(Long id) {
        return newsRepository.getNewsLink(id);
    }

    public List<News> findAllBylast() {
        Pageable pageable = PageRequest.of(0,2);
        return newsRepository.findTop2ByOrderByDateDesc(pageable);
    }

    public News findById(Long newsId) {
        return newsRepository.findById(newsId).orElse(null);
    }


    @Transactional
    public List<NewsCustomDto> findBydetail(String productId, int page, int size) {
        return newsRepository.findBydetail(productId,page,size);
    }

    @Transactional
    public int findByDetailCount(String productId) {
        return newsRepository.findByDetailCount(productId);
    }

    public void viewupdate(Long newsId) {
        newsRepository.updateViewCount(newsId);
    }

    public Page<NewsCustomDto> getAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if(sort.equals("views")) {
            return newsRepository.findBynewsByView(pageable);
        }else {
            return newsRepository.findBynewsById(pageable);
        }
    }

    public Page<NewsCustomDto> getByCategory(int page, int size, String sort, String category) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if(sort.equals("views")) {
            return newsRepository.findBynewsByViewAndCategory(category,pageable);
        }else {
            return newsRepository.findBynewsByIdAndCategory(category,pageable);
        }
    }
}
