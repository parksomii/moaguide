package com.moaguide.controller;

import com.moaguide.dto.NewDto.ArtBaseResponseDto;
import com.moaguide.dto.NewDto.ArtDetailDto;
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

    @GetMapping("/{product_Id}")
    public ResponseEntity<Object> detail(@PathVariable String product_Id) {
        ArtDetailDto artDetail = artService.findArtDetail(product_Id);
        return ResponseEntity.ok().body(artDetail);
    }

    @GetMapping("base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        ArtBaseResponseDto artBaseResponse = artService.findArtBase(product_Id);
        return ResponseEntity.ok(artBaseResponse);
    }

    @GetMapping("sub/{product_Id}")
    public ResponseEntity<Object> add(@PathVariable String product_Id) {
        return ResponseEntity.ok().body(null);
    }
}
