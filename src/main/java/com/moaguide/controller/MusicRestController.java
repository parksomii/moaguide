package com.moaguide.controller;

import com.moaguide.dto.NewDto.MusicBaseResponseDto;
import com.moaguide.dto.NewDto.customDto.MusicPublishDto;
import com.moaguide.dto.NewDto.customDto.MusicReponseDto;
import com.moaguide.dto.NewDto.customDto.MusicSongDto;
import com.moaguide.service.MusicDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
/*        MusicPublishDto music = musicService.findbase(product_Id);
        MusicSongDto musicSong = musicService.findsong(product_Id);*/
        MusicBaseResponseDto musicBase = musicService.findbase(product_Id);
        // 저작권료 정보
        return ResponseEntity.ok(musicBase);
    }

/*
    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> Sub(@PathVariable String product_Id) {
        MusicSubDto music = musicService.findsub(product_Id);
        return ResponseEntity.ok(new MusicSubResponseDto(music));
    }*/
}
