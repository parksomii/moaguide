package com.moaguide.controller;

import com.moaguide.dto.NewDto.MusicBaseResponseDto;
import com.moaguide.dto.NewDto.MusicSubResponseDto;
import com.moaguide.dto.NewDto.customDto.MusicDivideResponseDto;
import com.moaguide.dto.NewDto.customDto.MusicPublishDto;
import com.moaguide.dto.NewDto.customDto.MusicReponseDto;
import com.moaguide.dto.NewDto.customDto.MusicSongDto;
import com.moaguide.dto.NewDto.musicDto.*;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.CurrentDivideService;
import com.moaguide.service.DivideService;
import com.moaguide.service.MusicDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/music/")
public class MusicRestController {
    private final MusicDetailService musicService;
    private final DivideService divideService;
    private final CurrentDivideService currentDivideService;
    private final JWTUtil jwtUtil;

    // 최상단 기본정보
//    @GetMapping("{product_Id}")
//    public ResponseEntity<?> product(@PathVariable String product_Id, @RequestHeader("Authorization") String auth) {
//        String Nickname = jwtUtil.getNickname(auth.substring(7));
//        MusicReponseDto music = musicService.findBydetail(product_Id,Nickname);
//        return ResponseEntity.ok(music);
//    }

    @GetMapping("{product_Id}")
    public ResponseEntity<?> product(@PathVariable String product_Id) {
//        String Nickname = jwtUtil.getNickname(auth.substring(7));
        String Nickname = "moaguide";
        MusicReponseDto music = musicService.findBydetail(product_Id,Nickname);
        // null 체크
        if (music == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        return ResponseEntity.ok(music);
    }


    // 기본정보
    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        MusicPublishDto music = musicService.findBase(product_Id);
        MusicSongDto musicSong = musicService.findSong(product_Id);
        MusicDivideResponseDto musicDivide = musicService.findDivide(product_Id);
        // null 체크
        if (music == null || musicSong == null || musicDivide == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }

        return ResponseEntity.ok(new MusicBaseResponseDto(music, musicSong, musicDivide));
    }

    // 상세정보
    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> Sub(@PathVariable String product_Id) {
        MusicSubResponseDto youtube = musicService.findYoutube(product_Id);
        // null 체크
        if (youtube == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }

        return ResponseEntity.ok(youtube);
    }

    // 유튜브 조회수
    @GetMapping("view/{product_Id}")
    public ResponseEntity<?> view(@PathVariable String product_Id, @RequestParam int month) {
        // 조회수 (3개월, 6개월, 1년, 3년, 전체)
        List<ViewDto> youtubeView = musicService.findView(product_Id, month);
        // null 체크
        if (youtubeView == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }

        // 빈 리스트 체크
        if (youtubeView.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content available.");
        }

        return ResponseEntity.ok(youtubeView);
    }

    // 검색량
    @GetMapping("search/{product_Id}")
    public ResponseEntity<?> search(@PathVariable String product_Id, @RequestParam int month) {
        // 검색량 (3개월, 6개월, 1년, 전체)
        List<SearchDto> search = musicService.findSearch(product_Id, month);
//        SearchResultDto search = musicService.findSearch(product_Id, month);
        // null 체크
        if (search == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }

        // 빈 리스트 체크
        if (search.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content available.");
        }

        return ResponseEntity.ok(search);
    }

    // 스트리밍 수
    @GetMapping("streaming/{product_Id}")
    public ResponseEntity<?> streaming(@PathVariable String product_Id, @RequestParam int month) {
        // 스트리밍 수 (3개월, 6개월, 1년, 전체)
        List<SteamingDto> streaming = musicService.findStreaming(product_Id, month);
        // null 체크
        if (streaming == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }

        // 빈 리스트 체크
        if (streaming.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content available.");
        }

        return ResponseEntity.ok(streaming);
    }

    // 공연일정
    @GetMapping("consert/{product_Id}")
    public ResponseEntity<?> consert(@PathVariable String product_Id) {
        // 공연일정 최신순
        List<ConsertDto> consert = musicService.findConsert(product_Id);
        // null 체크
        if (consert == null) {
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }

        // 빈 리스트 체크
        if (consert.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content available.");
        }

        return ResponseEntity.ok(consert);
    }
}
