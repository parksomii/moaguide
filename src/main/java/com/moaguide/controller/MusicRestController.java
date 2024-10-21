package com.moaguide.controller;

import com.moaguide.dto.NewDto.MusicBaseResponseDto;
import com.moaguide.dto.NewDto.MusicSubResponseDto;
import com.moaguide.dto.NewDto.customDto.MusicDivideResponseDto;
import com.moaguide.dto.NewDto.customDto.MusicPublishDto;
import com.moaguide.dto.NewDto.customDto.MusicReponseDto;
import com.moaguide.dto.NewDto.customDto.MusicSongDto;
import com.moaguide.dto.NewDto.musicDto.ConsertDto;
import com.moaguide.dto.NewDto.musicDto.SearchDto;
import com.moaguide.dto.NewDto.musicDto.SteamingDto;
import com.moaguide.dto.NewDto.musicDto.ViewDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.MusicDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/music/")
public class MusicRestController {
    private final MusicDetailService musicService;
    private final JWTUtil jwtUtil;

    // 최상단 기본정보
    @GetMapping("{product_Id}")
    public ResponseEntity<?> product(@PathVariable String product_Id,  @RequestHeader(value="Authorization",required = false) String jwt) {
        String Nickname;
        if ( jwt!= null && jwt.startsWith("Bearer ")) {
            if(jwtUtil.isExpired(jwt.substring(7))){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Nickname = jwtUtil.getNickname(jwt.substring(7));
        }else{
            Nickname = "null";
        }
        MusicReponseDto music = musicService.findBydetail(product_Id,Nickname);
        return ResponseEntity.ok(music);
    }

    // 기본정보
    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        MusicPublishDto music = musicService.findBase(product_Id);
        MusicSongDto musicSong = musicService.findSong(product_Id);
        MusicDivideResponseDto musicDivide = musicService.findDivide(product_Id);
        // TODO
        return ResponseEntity.ok(new MusicBaseResponseDto(music, musicSong, musicDivide));
    }

    // 상세정보
    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> Sub(@PathVariable String product_Id) {
        MusicSubResponseDto youtube = musicService.findYoutube(product_Id);
        // TODO
        return ResponseEntity.ok(youtube);
    }

    // 유튜브 조회수
    @GetMapping("view/{product_Id}")
    public ResponseEntity<?> view(@PathVariable String product_Id, @RequestParam int month) {
        // 조회수 (3개월, 6개월, 1년, 3년, 전체)
        List<ViewDto> youtubeView = musicService.findView(product_Id, month);
        // 빈 리스트 체크
        if (youtubeView.isEmpty()) {
            return ResponseEntity.ok(new HashMap<>());
        }
        return ResponseEntity.ok(youtubeView);
    }

    // 검색량
    @GetMapping("search/{product_Id}")
    public ResponseEntity<?> search(@PathVariable String product_Id, @RequestParam int month) {
        // 검색량 (3개월, 6개월, 1년, 전체)
        List<SearchDto> search = musicService.findSearch(product_Id, month);
        // 빈 리스트 체크
        if (search.isEmpty()) {
            return ResponseEntity.ok(new HashMap<>());
        }
        return ResponseEntity.ok(search);
    }

    // 스트리밍 수
    @GetMapping("streaming/{product_Id}")
    public ResponseEntity<?> streaming(@PathVariable String product_Id, @RequestParam int month) {
        // 스트리밍 수 (3개월, 6개월, 1년, 전체)
        List<SteamingDto> streaming = musicService.findStreaming(product_Id, month);
        // 빈 리스트 체크
        if (streaming.isEmpty()) {
            return ResponseEntity.ok(new HashMap<>());
        }
        return ResponseEntity.ok(streaming);
    }

    // 공연일정
    @GetMapping("consert/{product_Id}")
    public ResponseEntity<?> consert(@PathVariable String product_Id) {
        // 공연일정 최신순 8개
        List<ConsertDto> consert = musicService.findConsert(product_Id);
        // 빈 리스트 체크
        if (consert.isEmpty()) {
            return ResponseEntity.ok(new HashMap<>());
        }
        return ResponseEntity.ok(consert);
    }
}
