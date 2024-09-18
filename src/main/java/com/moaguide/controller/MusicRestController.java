package com.moaguide.controller;

import com.moaguide.dto.NewDto.MusicBaseResponseDto;
import com.moaguide.dto.NewDto.MusicSubResponseDto;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.service.MusicDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        return ResponseEntity.ok(new MusicSubResponseDto());
    }*/
}
