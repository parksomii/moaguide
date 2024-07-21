package com.moaguide.service.Music;


import com.moaguide.domain.divide.MusicDivide;
import com.moaguide.domain.divide.MusicDivideRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.sql.Date;
import java.util.List;

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
