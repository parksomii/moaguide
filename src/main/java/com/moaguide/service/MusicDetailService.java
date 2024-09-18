package com.moaguide.service;

import com.moaguide.domain.detail.MusicDetailRepository;
import com.moaguide.domain.divide.CurrentDivideRepository;
import com.moaguide.domain.divide.DivideRepository;
import com.moaguide.domain.divide.MusicDivideRepository;
import com.moaguide.dto.NewDto.customDto.*;
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
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class MusicDetailService {
    private final MusicDetailRepository musicRepository;
    private final MusicDivideRepository musicDivideRepository;
    private final CurrentDivideRepository currentDivideRepository;
    private final DivideRepository divideRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(readOnly = false)
    public MusicReponseDto findBydetail(String productId) {
        MusicReponseDto music = musicRepository.findMusicDetail(productId);
        return music;
    }

    @Transactional(readOnly = false)
    public MusicPublishDto findBase(String productId){
        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("music_base")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId);

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            log.warn("Stored procedure {} returned no results for productId: {}", "music_base", productId);
            return null;
        }

        // 첫 번째 결과 반환
        Object[] result = resultList.get(0);

        if (result == null) {
            return null;
        }

        // MusicPublishDto
        MusicPublishDto publishDto = new MusicPublishDto(
                (String) result[0],  // name
                (String) result[1],  // type
                (String) result[2],  // singer
                (Integer) result[3],  // piece
                (Integer) result[4],  // basePrice
                (Long) result[5],     // totalPrice
                ((Date) result[6]).toLocalDate()  // issuDay
        );

        return publishDto;
    }

    @Transactional(readOnly = false)
    public MusicSongDto findSong(String productId){
        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("music_base")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId);

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            log.warn("Stored procedure {} returned no results for productId: {}", "music_song", productId);
            return null;
        }

        // 첫 번째 결과 반환
        Object[] result = resultList.get(0);

        if (result == null) {
            return null;
        }

        // MusicSongDto
        MusicSongDto songDto = new MusicSongDto(
                (String) result[7],  // introduceSong
                (String) result[8],  // genre
                (String) result[2],  // singer
                (String) result[9], // writer
                (String) result[10], // composing
                ((Date) result[11]).toLocalDate()  // announcementDate
        );

        return songDto;
    }

    @Transactional(readOnly = false)
    public MusicDivideResponseDto findDivide(String productId) {
        log.info("Starting stored procedure call for productId: {}", productId);

        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("music_divied")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId);

        log.info("Stored procedure created, about to execute");

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();

        log.info("Stored procedure executed, result size: {}", resultList.size());

        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            log.warn("Stored procedure {} returned no results for productId: {}", "music_divide", productId);
            return null;
        }

        // 첫 번째 결과 반환
        Object[] result = resultList.get(0);

        log.debug("First result: {}", Arrays.toString(result));

        if (result == null) {
            log.warn("Stored procedure returned null result for productId: {}", productId);
            return null;
        }

        // MusicDivideDto에 필요한 값을 추출하여 DTO 생성
        MusicDivideDto musicDivideDto = new MusicDivideDto(
                ((BigDecimal) result[2]).doubleValue(), // Broadcasting
                ((BigDecimal) result[3]).doubleValue(), // Transfer
                ((BigDecimal) result[4]).doubleValue(), // Replication
                ((BigDecimal) result[5]).doubleValue(), // Performance
                ((BigDecimal) result[6]).doubleValue(), // Deum
                ((BigDecimal) result[7]).doubleValue()  // Etc
        );

        log.debug("MusicDivideDto created: {}", musicDivideDto);

        // MusicDivideResponseDto에 필요한 값을 추출하여 DTO 생성
        MusicDivideResponseDto divideResponseDto = new MusicDivideResponseDto(
                ((BigDecimal) result[0]).doubleValue(),      // lastDividend
                ((BigDecimal) result[1]).doubleValue(),      // lastDividendRate
                musicDivideDto,                              // 저작권료 상세정보(MusicDivideDto)
                ((Date) result[8]).toLocalDate(),            // paymentDay
                (Integer) result[9]                          // divideCycle
        );

        log.debug("MusicDivideResponseDto created: {}", divideResponseDto);

        return divideResponseDto;
    }

    public List<DivideCustomDto> findAllByproductId(String id) {
        List<DivideCustomDto> divideList = divideRepository.findAllById(id);
        log.info("*************** 배당금 리스트: {}", divideList);
        return divideList;
    }
}
