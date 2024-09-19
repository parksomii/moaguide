package com.moaguide.controller;


import com.moaguide.dto.NewDto.ContentDetailDto;
import com.moaguide.dto.NewDto.customDto.ContentBaseDto;
import com.moaguide.dto.NewDto.customDto.MovieInfoDto;
import com.moaguide.service.ContentService;
import com.moaguide.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/Content/")
public class ContentRestController {
    private final ContentService contentService;
    private final MovieService movieService;

    @GetMapping("{product_Id}")
    public ResponseEntity<ContentDetailDto> getContent(@PathVariable String product_Id) {
        ContentDetailDto contentDetailDto = contentService.findDetail(product_Id);
        return ResponseEntity.ok(contentDetailDto);
    }

    @GetMapping("base/{product_Id}")
    public ResponseEntity<?> base(@PathVariable String product_Id, @RequestParam String genre) {
        ContentBaseDto base = contentService.findBase(product_Id, genre);
        return ResponseEntity.ok(base);
    }

//    @GetMapping("inform/{product_Id}")
//    public ResponseEntity<?> inform(@PathVariable String product_Id, @RequestParam String genre) {
//        if(genre.equals("MOVIE")){
//            MovieInfoDto movieInfoDto =
//        } else if (genre.equals("EXHIBITION")) {
//
//        }else if (genre.equals("Drama") || genre.equals("ANIMATION")) {
//
//        }  else if(genre.equals("CULTURE")){
//
//        }else if(genre.equals("TRAVEL")){
//
//        }else{
//            return ResponseEntity.ok("");
//        }
//    }
}
