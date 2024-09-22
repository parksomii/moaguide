package com.moaguide.controller;

import com.moaguide.dto.NewDto.DetailDivideResponseDto;
import com.moaguide.dto.NewDto.MusicBaseResponseDto;
import com.moaguide.dto.NewDto.MusicSubResponseDto;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.dto.NewDto.musicDto.ConsertDto;
import com.moaguide.dto.NewDto.musicDto.SearchDto;
import com.moaguide.dto.NewDto.musicDto.SteamingDto;
import com.moaguide.dto.NewDto.musicDto.ViewDto;
import com.moaguide.service.CurrentDivideService;
import com.moaguide.service.DivideService;
import com.moaguide.service.MusicDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/music/")
public class MusicRestController {
    private final MusicDetailService musicService;
    private final DivideService divideService;
    private final CurrentDivideService currentDivideService;

    // 최상단 기본정보
    @GetMapping("{product_Id}")
    public ResponseEntity<?> product(@PathVariable String product_Id) {
        MusicReponseDto music = musicService.findBydetail(product_Id);
        return ResponseEntity.ok(music);
    }

    // 기본정보
    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        MusicPublishDto music = musicService.findBase(product_Id);
        MusicSongDto musicSong = musicService.findSong(product_Id);
        MusicDivideResponseDto musicDivide = musicService.findDivide(product_Id);
        return ResponseEntity.ok(new MusicBaseResponseDto(music, musicSong, musicDivide));
    }

    // 저작권료 그래프
    @GetMapping("divide/{product_Id}")
    public ResponseEntity<Object> divide(@PathVariable String product_Id, @RequestParam String date) {
        List<DivideCustomDto> divideCustomDtos = divideService.getAllProductIdByDate(product_Id, date);
        Integer divideCycle = currentDivideService.findCycle(product_Id);
        return ResponseEntity.ok().body(new DetailDivideResponseDto(divideCustomDtos,divideCycle));
    }

    // 상세정보
    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> Sub(@PathVariable String product_Id) {
        MusicSubResponseDto youtube = musicService.findYoutube(product_Id);
        return ResponseEntity.ok(youtube);
    }

    // 유튜브 조회수
    @GetMapping("view/{product_Id}")
    public ResponseEntity<?> view(@PathVariable String product_Id, @RequestParam String date) {
        // 조회수 (6개월, 1년, 3년, 전체)
        List<ViewDto> youtubeView = musicService.findView(product_Id, date);
        return ResponseEntity.ok(youtubeView);
    }

    // 검색량
    @GetMapping("search/{product_Id}")
    public ResponseEntity<?> search(@PathVariable String product_Id, @RequestParam String date) {
        // 검색량 (일주일, 6개월, 1년, 전체)
        List<SearchDto> search = musicService.findSearch(product_Id, date);
        return ResponseEntity.ok(search);
    }

    // 스트리밍 수
    @GetMapping("streaming/{product_Id}")
    public ResponseEntity<?> streaming(@PathVariable String product_Id, @RequestParam String date) {
        // 스트리밍 수 (일주일, 6개월, 1년, 전체)
        List<SteamingDto> streaming = musicService.findStreaming(product_Id, date);
        return ResponseEntity.ok(streaming);
    }

    // 공연일정
    @GetMapping("consert/{product_Id}")
    public ResponseEntity<?> consert(@PathVariable String product_Id) {
        // 공연일정 최신순
        List<ConsertDto> consert = musicService.findConsert(product_Id);
        return ResponseEntity.ok(consert);
    }
}
