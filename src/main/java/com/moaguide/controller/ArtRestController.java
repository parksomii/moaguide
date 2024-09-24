package com.moaguide.controller;

import com.moaguide.dto.NewDto.ArtBaseResponseDto;
import com.moaguide.dto.NewDto.ArtDetailDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.art.ArtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/art")
public class ArtRestController {
    private final ArtService artService;
    private final JWTUtil jwtUtil;


//    @GetMapping("/{product_Id}")
//    public ResponseEntity<Object> detail(@PathVariable String product_Id, @RequestHeader("Authorization") String auth) {
//        String Nickname = jwtUtil.getNickname(auth.substring(7));
//        ArtDetailDto artDetail = artService.findArtDetail(product_Id,Nickname);
//        return ResponseEntity.ok().body(artDetail);
//    }

    @GetMapping("/{product_Id}")
    public ResponseEntity<Object> detail(@PathVariable String product_Id) {
        String Nickname = "moaguide";
        ArtDetailDto artDetail = artService.findArtDetail(product_Id,Nickname);
        return ResponseEntity.ok().body(artDetail);
    }

    @GetMapping("/base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        ArtBaseResponseDto artBaseResponse = artService.findArtBase(product_Id);
        return ResponseEntity.ok(artBaseResponse);
    }

    @GetMapping("/sub/{product_Id}")
    public ResponseEntity<Object> add(@PathVariable String product_Id) {

        return ResponseEntity.ok().body(null);
    }
}
