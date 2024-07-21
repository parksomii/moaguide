package com.moaguide.service;

import com.moaguide.domain.detail.MusicDetailRepository;
import com.moaguide.dto.MusicDetailDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class MusicService {
    private final MusicDetailRepository musicRepository;

    public MusicDetailDto detail(String id) {
        MusicDetailDto musicDetailDto = musicRepository.findByproductId(id).toDTO();
        return musicDetailDto;
    }
}
