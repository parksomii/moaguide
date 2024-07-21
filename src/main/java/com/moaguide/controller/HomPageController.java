package com.moaguide.controller;


import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.news.News;
import com.moaguide.dto.SummaryListDto;
import com.moaguide.service.DivideService;
import com.moaguide.service.LoginService;
import com.moaguide.service.Music.MusicDivideService;
import com.moaguide.service.NewsService;
import com.moaguide.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class HomPageController {
    private final LoginService loginService;
    private final DivideService divideService;
    private final MusicDivideService musicDivideService;
    private final TransactionService transactionService;
    private final NewsService newsService;

    @GetMapping("/")
    public String homepage(Model model) {
        List<Divide> divideList = divideService.topthree();
        List<SummaryListDto> datas = transactionService.findByPrdocutId(divideList);
        String id = loginService.loginId();
        String role = loginService.loginRole();
        List<News> news = newsService.findAllBylast();
        log.info("id값과 권한"+id + "," + role);
        model.addAttribute("Id", id);
        model.addAttribute("role",role);
        model.addAttribute("datas",datas);
        model.addAttribute("news",news);
        return "index";
    }
}
