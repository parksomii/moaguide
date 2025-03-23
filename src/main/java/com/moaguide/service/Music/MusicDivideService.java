package com.moaguide.service.Music;


import com.moaguide.refactor.product.entity.MusicDivide;
import com.moaguide.refactor.product.repository.MusicDivideRepository;
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
