package com.moaguide.controller;



import com.moaguide.dto.NewDto.ContentDetailDto;
import com.moaguide.dto.NewDto.customDto.TravelInfoDto;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.service.ContentService;
import com.moaguide.service.ContentSubService;
import com.moaguide.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/content/")
public class ContentRestController {
    private final ContentService contentService;
    private final MovieService movieService;
    private final ContentSubService contentSubService;


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

    @GetMapping("inform/{product_Id}")
    public ResponseEntity<?> inform(@PathVariable String product_Id, @RequestParam String genre) {
        if(genre.equals("MOVIE")){
            MovieInfoDto movie = movieService.findmovie(product_Id);
            return ResponseEntity.ok(movie);
        } else if (genre.equals("EXHIBITION")) {
            ExhibitInfoDto exhibitInfoDto = contentSubService.findexhibit(product_Id);
            return ResponseEntity.ok(exhibitInfoDto);
        }else if (genre.equals("Drama") || genre.equals("ANIMATION")) {
            BroadcastInfoDto broadcast = contentSubService.findbroadcast(product_Id);
            return ResponseEntity.ok(broadcast);
        }  else if(genre.equals("CULTURE")){
            PerformanceInfoDto performance = contentSubService.findperformance(product_Id);
            return ResponseEntity.ok(performance);
        }else if(genre.equals("TRAVEL")){
            TravelInfoDto travel = contentSubService.findtravel(product_Id);
            return ResponseEntity.ok(travel);
        }else{
            return ResponseEntity.ok("");
        }
    }
}
