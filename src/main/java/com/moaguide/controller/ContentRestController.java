package com.moaguide.controller;


import com.moaguide.dto.NewDto.ContentDetailDto;
import com.moaguide.dto.NewDto.customDto.ContentBaseDto;
import com.moaguide.service.ContentService;
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

    @GetMapping("/{product_Id}")
    public ResponseEntity<ContentDetailDto> getContent(@PathVariable String product_Id) {
        ContentDetailDto contentDetailDto = contentService.findDetail(product_Id);
        return  ResponseEntity.ok(contentDetailDto);
    }

    @GetMapping("base/{product_Id}")
    public ResponseEntity<?> product(@PathVariable String product_Id,@RequestParam String genre) {
            ContentBaseDto base = contentService.findBase(product_Id,genre);
            return ResponseEntity.ok(base);
    }
}
