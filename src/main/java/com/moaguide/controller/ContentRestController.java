package com.moaguide.controller;


import com.moaguide.domain.content.movie.MovieStats;
import com.moaguide.dto.NewDto.ContentDetailDto;
import com.moaguide.dto.NewDto.ContentsSubResponseDto;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.service.ContentService;
import com.moaguide.service.ContentSubService;
import com.moaguide.service.MovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/detail/contents")
public class ContentRestController {
    private final ContentService contentService;
    private final MovieService movieService;
    private final ContentSubService contentSubService;


    @GetMapping("/{product_Id}")
    public ResponseEntity<?> getContent(@PathVariable String product_Id) {
        ContentDetailDto contentDetailDto = contentService.findDetail(product_Id);
        return ResponseEntity.ok(contentDetailDto);
    }

    @GetMapping("/base/{product_Id}")
    public ResponseEntity<?> base(@PathVariable String product_Id, @RequestParam String genre) {
        ContentBaseDto base = contentService.findBase(product_Id, genre);
        return ResponseEntity.ok(base);
    }

    @GetMapping("/inform/{product_Id}")
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

    @GetMapping("/sub/{product_Id}")
    public ResponseEntity<?> schedule(@PathVariable String product_Id) {
        List<MovieScheduleDto> movieScheduleDtos = movieService.findSechedule(product_Id);
        List<MovieStats> movieStats = movieService.findStats(product_Id);
        return ResponseEntity.ok(new ContentsSubResponseDto(movieScheduleDtos,movieStats));
    }

    @GetMapping("/screen/{product_Id}")
    public ResponseEntity<?> screen(@PathVariable String product_Id) {
        List<MovieSubDto>  screen = contentSubService.findScreen(product_Id);
        return ResponseEntity.ok(screen);
    }

    @GetMapping("/screen/ten/{product_Id}")
    public ResponseEntity<?> screenten(@PathVariable String product_Id) {
        Pageable pageable = Pageable.ofSize(10);
        List<MovieSubDto>  screen = contentSubService.findScreenten(product_Id,pageable);
        return ResponseEntity.ok(screen);
    }

    @GetMapping("/showtime/{product_Id}")
    public ResponseEntity<?> showtime(@PathVariable String product_Id) {
        List<MovieSubDto> showtimes = contentSubService.findshowtime(product_Id);
        return ResponseEntity.ok(showtimes);
    }

    @GetMapping("/showtime/ten/{product_Id}")
    public ResponseEntity<?> showtimeten(@PathVariable String product_Id) {
        Pageable pageable = Pageable.ofSize(10);
        List<MovieSubDto>  screen = contentSubService.findshowtimeten(product_Id,pageable);
        return ResponseEntity.ok(screen);
    }

    @GetMapping("/audience/{product_Id}")
    public ResponseEntity<?> Audience(@PathVariable String product_Id) {
        List<MovieSubDto> showtimes = contentSubService.findaudience(product_Id);
        return ResponseEntity.ok(showtimes);
    }

    @GetMapping("/audience/ten/{product_Id}")
    public ResponseEntity<?> Audienceten(@PathVariable String product_Id) {
        Pageable pageable = Pageable.ofSize(10);
        List<MovieSubDto>  screen = contentSubService.findaudienceten(product_Id,pageable);
        return ResponseEntity.ok(screen);
    }

    @GetMapping("/revenue/{product_Id}")
    public ResponseEntity<?> revenue(@PathVariable String product_Id) {
        List<MovieSubDto> showtimes = contentSubService.findrevenue(product_Id);
        return ResponseEntity.ok(showtimes);
    }

    @GetMapping("/revenue/ten/{product_Id}")
    public ResponseEntity<?> revenueten(@PathVariable String product_Id) {
        Pageable pageable = Pageable.ofSize(10);
        List<MovieSubDto>  screen = contentSubService.findrevenueten(product_Id,pageable);
        return ResponseEntity.ok(screen);
    }

    @GetMapping("/rank/{product_Id}")
    public ResponseEntity<?> rank(@PathVariable String product_Id) {
        List<MovieSubDto> showtimes = contentSubService.findrank(product_Id);
        return ResponseEntity.ok(showtimes);
    }

    @GetMapping("/rank/ten/{product_Id}")
    public ResponseEntity<?> rankten(@PathVariable String product_Id) {
        Pageable pageable = Pageable.ofSize(10);
        List<MovieSubDto>  screen = contentSubService.findrankten(product_Id,pageable);
        return ResponseEntity.ok(screen);
    }

}
