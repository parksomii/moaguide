package com.moaguide.service;

import com.moaguide.domain.detail.MusicDetailRepository;
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

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class MusicDetailService {
    private final MusicDetailRepository musicRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public MusicReponseDto findBydetail(String productId) {
        MusicReponseDto music = musicRepository.findMusicDetail(productId);
        return music;
    }

    @Transactional(readOnly = true)
    public MusicPublishDto findbase(String productId) {
        Object[] result = executeStoredProcedure("music_base", productId);
        if (result == null) {
            return null;
        }

        return new MusicPublishDto(
                (String) result[0],  // name
                (String) result[1],  // type
                (String) result[2],  // singer
                (Integer) result[3],  // piece
                (Integer) result[4],  // basePrice
                (Long) result[5],     // totalPrice
                ((Date) result[6]).toLocalDate()  // issuDay
        );
    }

    @Transactional(readOnly = true)
    public MusicSongDto findsong(String productId) {
        Object[] result = executeStoredProcedure("music_base", productId);
        if (result == null) {
            return null;
        }

        return new MusicSongDto(
                (String) result[7],  // introduce_song
                (String) result[8],  // genre
                (String) result[2],  // singer
                (String) result[9],  // writer
                (String) result[10], // composing
                ((Date) result[11]).toLocalDate()  // announcement_date
        );
    }

    private Object[] executeStoredProcedure(String procedureName, String productId) {
        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery(procedureName)
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId);

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            log.warn("Stored procedure {} returned no results for productId: {}", procedureName, productId);
            return null;
        }

        // 첫 번째 결과 반환
        return resultList.get(0);
    }
}
