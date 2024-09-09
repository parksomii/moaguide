package com.moaguide.controller;

import com.moaguide.dto.NewDto.ArtBaseResponseDto;
import com.moaguide.dto.NewDto.customDto.ArtAuthorDto;
import com.moaguide.dto.NewDto.customDto.ArtPublishDto;
import com.moaguide.dto.NewDto.customDto.ArtWorkDto;
import com.moaguide.service.art.ArtService;
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
@RequestMapping("/detail/art/")
public class ArtRestController {
    private final ArtService artService;

    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        ArtPublishDto art = artService.findArt(product_Id);
        ArtAuthorDto author = artService.findAuthor(product_Id);
        ArtWorkDto work = artService.findWork(product_Id);
        return ResponseEntity.ok(new ArtBaseResponseDto(art,author,work));
    }

    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> add(@PathVariable String product_Id) {
        return ResponseEntity.ok().body(null);
    }
}
