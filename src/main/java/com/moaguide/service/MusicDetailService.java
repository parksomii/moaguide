package com.moaguide.service;

import com.moaguide.domain.detail.MusicDetailRepository;
import com.moaguide.dto.MusicDetailDto;
import com.moaguide.dto.NewDto.customDto.MusicPublishDto;
import com.moaguide.dto.NewDto.customDto.MusicReponseDto;
import com.moaguide.dto.NewDto.customDto.MusicSongDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class MusicDetailService {
    private final MusicDetailRepository musicRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(readOnly = false)
    public MusicReponseDto findBydetail(String productId) {
        MusicReponseDto music = musicRepository.findMusicDetail(productId);
        return music;
    }

    public MusicPublishDto findbase(String productId) {
        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("music_base")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId);

        // 프로시저 실행
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 리턴
        if (resultList.isEmpty()) {
            return null;
        }

        // 결과값을 DTO에 매핑
        Object[] result = resultList.get(0);  // 첫 번째 결과만 사용

        // MusicReponseDto 매핑
        return new MusicPublishDto(
                (String) result[0],  // name
                (String) result[1],  // type
                (String) result[2], // singer
                ((Integer) result[3]),  // piece (Integer 그대로 사용)
                ((Integer) result[4]),  // basePrice (Integer -> Long 변환)
                ((Long) result[5]),  // totalPrice (Long 그대로 사용
                ((Date) result[6]).toLocalDate()  // issuDay
        );
    }

    public MusicSongDto findsong(String productId) {
        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("music_base")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId);

        // 프로시저 실행
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 리턴
        if (resultList.isEmpty()) {
            return null;
        }

        // 결과값을 DTO에 매핑
        Object[] result = resultList.get(0);  // 첫 번째 결과만 사용

        // MusicSongDto 매핑
        return new MusicSongDto(
                (String) result[7],  // introduce_song
                (String) result[8],  // genre
                (String) result[2],  // singer
                (String) result[9],  // writer
                (String) result[10],  // composing
                ((Date) result[11]).toLocalDate()  // announcement_date
        );
    }
}