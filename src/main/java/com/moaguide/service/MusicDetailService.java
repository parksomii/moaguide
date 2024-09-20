package com.moaguide.service;

import com.moaguide.domain.detail.MusicDetailRepository;
import com.moaguide.domain.divide.CurrentDivideRepository;
import com.moaguide.domain.divide.DivideRepository;
import com.moaguide.domain.divide.MusicDivideRepository;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.dto.NewDto.musicDto.ConsertDto;
import com.moaguide.dto.NewDto.musicDto.SearchDto;
import com.moaguide.dto.NewDto.musicDto.SteamingDto;
import com.moaguide.dto.NewDto.musicDto.ViewDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<ViewDto> findView(String productId, String date) {
        // 유튜브 조회수 (6개월, 1년, 3년, 전체)
        // 현재 날짜 기준으로 날짜 계산
        LocalDate day = LocalDate.now();

        if (date.equals("6month")) {
            day = day.minusMonths(6); // 6개월 전
        } else if (date.equals("1year")) {
            day = day.minusYears(1); // 1년 전
        } else if (date.equals("3year")) {
            day = day.minusYears(3); // 3년 전
        } else if (date.equals("all")) {
            day = LocalDate.of(1900, 1, 1); // 전체 기간 조회를 위한 과거 날짜
        } else {
            return null;
        }
        // StoredProcedureQuery 객체 생성

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("music_view")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_Start_Date", Date.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId)
                .setParameter("in_Start_Date", Date.valueOf(day));

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            log.warn("Stored procedure {} returned no results for productId: {}", "music_view", productId);
            return null;
        }

        // 결과 처리
        List<ViewDto> viewList = new ArrayList<>();
        for (Object[] result : resultList) {
            ViewDto viewDto = new ViewDto(
                    (String) result[0],  // 조회수
                    ((Date) result[1]).toLocalDate().toString()  // 조회 날짜를 String으로 변환
            );
            viewList.add(viewDto);
        }

        return viewList;
    }

    public List<SearchDto> findSearch(String productId, String date) {
        // 현재 날짜 기준으로 날짜 계산
        LocalDate day = LocalDate.now();

        if (date.equals("6month")) {
            day = day.minusMonths(6); // 6개월 전
        } else if (date.equals("1year")) {
            day = day.minusYears(1); // 1년 전
        } else if (date.equals("3year")) {
            day = day.minusYears(3); // 3년 전
        } else if (date.equals("all")) {
            day = LocalDate.of(1900, 1, 1); // 전체 기간 조회를 위한 과거 날짜
        } else {
            return null;
        }

        // 1. MusicDetail에서 데이터 검색 (프로시저 호출)
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("music_search")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_Start_Date", Date.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId)
                .setParameter("in_Start_Date", Date.valueOf(day));

        List<Object[]> resultList = query.getResultList();

        // 2. MusicDetail에서 결과가 없으면 Product에서 검색
        if (resultList.isEmpty()) {
            log.warn("No results found in MusicDetail, checking Product for productId: {}", productId);

            // Product 프로시저 호출
            query = entityManager
                    .createStoredProcedureQuery("product_search")
                    .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("in_Start_Date", Date.class, ParameterMode.IN)
                    .setParameter("in_Product_Id", productId)
                    .setParameter("in_Start_Date", Date.valueOf(day));

            resultList = query.getResultList();

            // Product에서도 결과가 없으면 null 반환
            if (resultList.isEmpty()) {
                log.warn("No results found in Product for productId: {}", productId);
                return null;
            }
        }

        // 결과 처리
        List<SearchDto> searchList = new ArrayList<>();
        for (Object[] result : resultList) {
            SearchDto searchDto = new SearchDto(
                    (String) result[1],  // 검색량 (value)
                    (String) result[0]  // 검색 날짜 (viewDay)
            );
            searchList.add(searchDto);
        }

        return searchList;
    }

    public List<SteamingDto> findStreaming(String productId, String date) {
        // 스트리밍 수 (6개월, 1년, 3년, 전체)
        // 현재 날짜 기준으로 날짜 계산
        LocalDate day = LocalDate.now();

        if (date.equals("week")) {
            day = day.minusWeeks(1); // 1주일 전
        } else if (date.equals("6month")) {
            day = day.minusMonths(6); // 6개월 전
        } else if (date.equals("1year")) {
            day = day.minusYears(1); // 1년 전
        } else if (date.equals("all")) {
            day = LocalDate.of(1900, 1, 1); // 전체 기간 조회를 위한 과거 날짜
        } else {
            return null;
        }

        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("music_streaming")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_Start_Date", Date.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId)
                .setParameter("in_Start_Date", Date.valueOf(day));

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            log.warn("Stored procedure {} returned no results for productId: {}", "music_streaming", productId);
            return null;
        }

        // 결과 처리
        List<SteamingDto> streamingList = new ArrayList<>();
        for (Object[] result : resultList) {
            SteamingDto steamingDto = new SteamingDto(
                    (String) result[0],  // 스트리밍 수
                    ((Date) result[1]).toLocalDate().toString()  // 스트리밍 날짜를 String으로 변환
            );
            streamingList.add(steamingDto);
        }

        return streamingList;
    }

    public List<ConsertDto> findConsert(String productId) {
        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("consert_keyword")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId);

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            log.warn("Stored procedure {} returned no results for productId: {}", "consert_keyword", productId);
            return null;
        }

        // 결과 처리
        List<ConsertDto> consertList = new ArrayList<>();
        for (Object[] result : resultList) {
            ConsertDto consertDto = new ConsertDto(
                    (String) result[0],  // title
                    (String) result[1],  // place
                    (String) result[2],  // period
                    (String) result[3]   // imageUrl
            );
            consertList.add(consertDto);
        }

        return consertList;
    }
}
