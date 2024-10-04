package com.moaguide.controller;

import com.moaguide.dto.NewDto.ArtBaseResponseDto;
import com.moaguide.dto.NewDto.ArtDetailDto;
import com.moaguide.jwt.JWTUtil;
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
        // null 체크
        if(artDetail == null){
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        return ResponseEntity.ok().body(artDetail);
    }

    @GetMapping("/base/{product_Id}")
    public ResponseEntity<Object> Base(@PathVariable String product_Id) {
        ArtBaseResponseDto artBaseResponse = artService.findArtBase(product_Id);
        // null 체크
        if(artBaseResponse == null){
            return ResponseEntity.badRequest().body("Invalid request: No data found.");
        }
        return ResponseEntity.ok(artBaseResponse);
    }

//    @GetMapping("/sub/{product_Id}")
//    public ResponseEntity<Object> add(@PathVariable String product_Id) {
//        // null 체크
//        return ResponseEntity.ok().body(null);
//    }
}
