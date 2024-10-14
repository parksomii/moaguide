package com.moaguide.controller;

import com.moaguide.dto.NewDto.ArtBaseResponseDto;
import com.moaguide.dto.NewDto.ArtDetailDto;
import com.moaguide.jwt.JWTUtil;
import com.moaguide.service.art.ArtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/art")
public class ArtRestController {
    private final ArtService artService;
    private final JWTUtil jwtUtil;


    @GetMapping("/{product_Id}")
    public ResponseEntity<Object> detail(@PathVariable String product_Id, @RequestHeader(value = "Authorization", required = false) String jwt) {
        String Nickname;
        if ( jwt!= null && jwt.startsWith("Bearer ")) {
            if(jwtUtil.isExpired(jwt.substring(7))){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Nickname = jwtUtil.getNickname(jwt.substring(7));
        }else{
            Nickname = null;
        }
        ArtDetailDto artDetail = artService.findArtDetail(product_Id,Nickname);
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

}
