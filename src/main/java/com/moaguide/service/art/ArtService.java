package com.moaguide.service.art;

import com.moaguide.domain.art.ArtAuthorRepository;
import com.moaguide.domain.detail.ArtDetailRepository;
import com.moaguide.dto.NewDto.ArtBaseResponseDto;
import com.moaguide.dto.NewDto.customDto.ArtAuthorDto;
import com.moaguide.dto.NewDto.customDto.ArtPublishDto;
import com.moaguide.dto.NewDto.customDto.ArtWorkDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class ArtService {
    private final ArtDetailRepository artRepository;
    private final ArtAuthorRepository authorRepository;

    public ArtPublishDto findArt(String productId) {
        ArtPublishDto art = artRepository.findArtDetail(productId);
        return art;
    }

    public ArtAuthorDto findAuthor(String productId) {
        ArtAuthorDto author = authorRepository.findAuthorDetail(productId);
        return author;
    }

    public ArtWorkDto findWork(String productId) {
        ArtWorkDto work = artRepository.findWorkDetail(productId);
        return work;
    }
}
