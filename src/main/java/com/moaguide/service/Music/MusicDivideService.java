package com.moaguide.service.Music;


import com.moaguide.domain.divide.MusicDivide;
import com.moaguide.domain.divide.MusicDivideRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MusicDivideService {
    private final MusicDivideRepository musicDivideRepository;

    @Transactional
    public MusicDivide findOne(String id) {
        MusicDivide musicDivide = musicDivideRepository.findByProductIdANDONE(id);
        return musicDivide;
    }
}
