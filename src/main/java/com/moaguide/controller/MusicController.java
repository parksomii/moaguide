package com.moaguide.controller;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.divide.MusicDivide;
import com.moaguide.domain.news.News;
import com.moaguide.domain.summary.Summary;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.MusicDetailDto;
import com.moaguide.dto.PageRequestDTO;
import com.moaguide.dto.PageResponseDTO;
import com.moaguide.service.*;
import com.moaguide.service.Music.MusicDivideService;
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
@Slf4j
@AllArgsConstructor
@RequestMapping("/summary/detail")
public class MusicController {
    private final SummaryService summaryService;
    private final MusicService musicService;
    private final TransactionService transactionService;
    private final MusicDivideService musicdivideService;
    private final NewsService newsService;
    private final DivideService divideService;

    // 음악 상세 페이지
    @GetMapping("/frame/music/{id}")
    public String detalePage(Model model, @PathVariable String id) {
        log.info("Controller detalePage GET - id: {}", id);
        Summary summary = summaryService.findById(id);
        log.info("Controller detalePage GET - summary: {}", summary);
        if(summary.getPlatformId().getPlatformId() % 2 == 0){
            return "saleComplet";
        }
        else {
            MusicDetailDto musicDetailDto = musicService.detail(id);
            List<Transaction> transactionList = transactionService.threeMonthgraph(id);
            List<Divide> divide = divideService.threeMonthgraph(id);
            log.info("Controller detalePage GET - musicDetailDto: {}", musicDetailDto);
            model.addAttribute("transaction", transactionList);
            model.addAttribute("music", musicDetailDto);
            model.addAttribute("divide", divide);
        }
        return "Music/MusicBase";
    }

    @GetMapping("/frame/music/news/{keyword}")
    public String search(Model model, @PathVariable("keyword") String keyword, PageRequestDTO pageRequestDTO) {
        Page<News> findNews = newsService.search(keyword, pageRequestDTO);
        log.info("*************** BuildingController GET /findNews" + findNews);

        // 검색된 뉴스를 DTO로 변환하여 리스트에 담음
        List<News> newsDtoList = findNews.getContent().stream()
                .map(News::toDto)
                .toList();
        log.info("*************** BuildingController GET /newsDtoList" + newsDtoList);

        // 페이지 응답 DTO 생성
        PageResponseDTO pageResponseDTO = new PageResponseDTO(newsDtoList, pageRequestDTO, findNews.getTotalElements());

        // 모델에 페이지 응답 DTO와 키워드 추가
        model.addAttribute("pageDTO", pageResponseDTO);
        model.addAttribute("news", newsDtoList);
        // 뉴스 페이지로 이동
        return "detail/DetailNews";
    }
}
