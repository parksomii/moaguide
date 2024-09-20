package com.moaguide.controller;

import com.moaguide.dto.NewDto.MusicBaseResponseDto;
import com.moaguide.dto.NewDto.MusicSubResponseDto;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.dto.NewDto.musicDto.ConsertDto;
import com.moaguide.dto.NewDto.musicDto.SearchDto;
import com.moaguide.dto.NewDto.musicDto.SteamingDto;
import com.moaguide.dto.NewDto.musicDto.ViewDto;
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

    @GetMapping("{product_Id}")
    public ResponseEntity<?> product(@PathVariable String product_Id) {
        MusicReponseDto music = musicService.findBydetail(product_Id);
        return ResponseEntity.ok(music);
    }

    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        MusicPublishDto music = musicService.findBase(product_Id);
        MusicSongDto musicSong = musicService.findSong(product_Id);
        MusicDivideResponseDto musicDivide = musicService.findDivide(product_Id);
        List<DivideCustomDto> divideCustomDtoList = musicService.findAllByproductId(product_Id);
        return ResponseEntity.ok(new MusicBaseResponseDto(music, musicSong, musicDivide, divideCustomDtoList));
    }

/*    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> Sub(@PathVariable String product_Id) {
    MusicYoutubeDto music = musicService.findYoutube(product_Id);
        return ResponseEntity.ok(new MusicSubResponseDto());
    }*/

    @GetMapping("view/{product_Id}")
    public ResponseEntity<?> view(@PathVariable String product_Id, @RequestParam String date) {
        // 스트리밍 수 (6개월, 1년, 3년, 전체)
        List<ViewDto> music = musicService.findView(product_Id, date);
        return ResponseEntity.ok(music);
    }

    @GetMapping("search/{product_Id}")
    public ResponseEntity<?> search(@PathVariable String product_Id, @RequestParam String date) {
        // 검색량 (일주일, 6개월, 1년, 전체)
        List<SearchDto> music = musicService.findSearch(product_Id, date);
        return ResponseEntity.ok(music);
    }

    @GetMapping("streaming/{product_Id}")
    public ResponseEntity<?> streaming(@PathVariable String product_Id, @RequestParam String date) {
        // 스트리밍 수 (일주일, 6개월, 1년, 전체)
        List<SteamingDto> music = musicService.findStreaming(product_Id, date);
        return ResponseEntity.ok(music);
    }

    @GetMapping("consert/{product_Id}")
    public ResponseEntity<?> consert(@PathVariable String product_Id) {
        // 공연일정 최신순
        List<ConsertDto> music = musicService.findConsert(product_Id);
        return ResponseEntity.ok(music);
    }
}
