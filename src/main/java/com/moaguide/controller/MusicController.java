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

}
