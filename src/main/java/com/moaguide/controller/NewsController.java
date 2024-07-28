package com.moaguide.controller;

import com.moaguide.domain.news.News;
import com.moaguide.dto.PageRequestDTO;
import com.moaguide.dto.PageResponseDTO;
import com.moaguide.service.NewsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/content/news/")
@Slf4j
public class NewsController {
    private final NewsService newsService;
    /*@GetMapping("/detail/{name}")
    public String building(@PathVariable String name, Model model) {

        return "building";
    }*/

/*    // 뉴스 페이지 출력
    @GetMapping
    public String newsList(Model model) {
        log.info("Controller GET newsList /news 출력");
        return "News/news";
    }*/

    // 최신순
    @GetMapping("/latest")
    public String latestNews(Model model, PageRequestDTO pageRequestDTO) {
        log.info("Controller GET latestNews /news/latest");
        log.info("Controller GET latestNews pageRequestDTO {} ", pageRequestDTO);
        // 뉴스 최신순으로 출력
        Page<News> latestList = newsService.getAllByLatest(pageRequestDTO);
        log.info("Controller GET latestNews result {} ", latestList);
        // DTO로 전달
        List<News> latestDtoList = latestList.getContent().stream()
                .map(News::toDto)
                .toList();
        log.info("Controller GET latestNews latestDtoList {} ", latestDtoList);
        // 뉴스 페이지로 전달
        PageResponseDTO pageResponseDTO = new PageResponseDTO(latestDtoList, pageRequestDTO, latestList.getTotalElements());
        model.addAttribute("news", latestList);
        model.addAttribute("pageDTO", pageResponseDTO);
        return "News/newsTable";
    }

    // 최근 많이 본 순
    @GetMapping
    public String mostViewNews(Model model, PageRequestDTO pageRequestDTO) {
        log.info("Controller GET mostViewNews /news/mostView");
        log.info("Controller GET mostViewNews pageRequestDTO {} ", pageRequestDTO);
        // 뉴스 조회수 순으로 출력
        Page<News> mostViewList = newsService.getAllByViews(pageRequestDTO);
        log.info("Controller GET mostViewNews result {} ", mostViewList);
        // DTO로 전달
        List<News> mostViewDtoList = mostViewList.getContent().stream()
                .map(News::toDto)
                .toList();
        log.info("Controller GET mostViewNews mostViewDtoList {} ", mostViewDtoList);
        // 뉴스 페이지로 전달
        PageResponseDTO pageResponseDTO = new PageResponseDTO(mostViewDtoList, pageRequestDTO, mostViewList.getTotalElements());
        model.addAttribute("news", mostViewList);
        model.addAttribute("pageDTO", pageResponseDTO);
        return "News/news";
    }

    // 뉴스 해당 링크 + 조회수 증가
    @GetMapping("/view/{id}")
    public String newsView(@PathVariable Long id) {
        newsService.incrementViewCount(id);
        String link = newsService.getNewsLink(id);
        return "redirect:" + link;
    }
}
