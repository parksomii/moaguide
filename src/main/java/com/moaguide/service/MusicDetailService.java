package com.moaguide.service;

import com.moaguide.domain.detail.MusicDetailRepository;
import com.moaguide.domain.divide.CurrentDivideRepository;
import com.moaguide.domain.divide.DivideRepository;
import com.moaguide.domain.divide.MusicDivideRepository;
import com.moaguide.dto.NewDto.MusicSubResponseDto;
import com.moaguide.dto.NewDto.customDto.*;
import com.moaguide.dto.NewDto.musicDto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class MusicDetailService {
    private final MusicDetailRepository musicRepository;
    private final MusicDivideRepository musicDivideRepository;
    private final CurrentDivideRepository currentDivideRepository;
    private final DivideRepository divideRepository;
    private final DataSource dataSource;

    @PersistenceContext
    private final EntityManager entityManager;

    // 최상단 기본정보
    @Transactional(readOnly = false)
    public MusicReponseDto findBydetail(String productId, String nickname) {
        LocalDate localDate = LocalDate.now().minusYears(1);
        MusicReponseDto music = musicRepository.findMusicDetail(productId,nickname,localDate.getYear());
        return music;
    }

    // 기본정보 : 발행정보
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
                result[1] == null ? "" : (String) result[1],  // type
                (String) result[2],  // singer
                (Integer) result[3],  // piece
                (Integer) result[4],  // basePrice
                (Long) result[5],     // totalPrice
                result[6] == null || "1990-01-01".equals(result[6].toString()) ? "" : ((Date) result[6]).toLocalDate().toString()
        );

        return publishDto;
    }

    // 기본정보 : 곡정보
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
                result[8] == null ? "" : (String) result[8],  // genre
                (String) result[2],  // singer
                (String) result[9], // writer
                (String) result[10], // composing
                ((Date) result[11]).toLocalDate()  // announcementDate
        );

        return songDto;
    }

    // 기본정보 : 저작권료
    @Transactional(readOnly = false)
    public MusicDivideResponseDto findDivide(String productId) {

        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("music_divied")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId);

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            return null;
        }

        // 첫 번째 결과 반환
        Object[] result = resultList.get(0);

        if (result == null) {
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

        // MusicDivideResponseDto에 필요한 값을 추출하여 DTO 생성
        MusicDivideResponseDto divideResponseDto = new MusicDivideResponseDto(
                ((BigDecimal) result[0]).doubleValue(),      // lastDividend
                ((BigDecimal) result[1]).doubleValue(),      // lastDividendRate
                musicDivideDto,                              // 저작권료 상세정보(MusicDivideDto)
                ((Date) result[8]).toLocalDate(),            // paymentDay
                (Integer) result[9]                          // divideCycle
        );

        return divideResponseDto;
    }

    // 상세정보
    public MusicSubResponseDto findYoutube(String productId) {
        MusicSubResponseDto youtube = musicRepository.findYoutube(productId);
        return youtube;
    }

    // 유튜브 조회수
    public List<ViewDto> findView(String productId, int month) {
        // 유튜브 조회수 (3개월, 6개월, 1년, 전체)
        // 현재 날짜 기준으로 날짜 계산
        LocalDate day = LocalDate.now();

        if (month == 3) {
            day = day.minusMonths(month); // 3개월 전
        } else if (month == 6) {
            day = day.minusMonths(month); // 6개월 전
        } else if (month == 12) {
            day = day.minusMonths(month); // 1년 전
        } else if(month == 36){
            day = day.minusMonths(month); // 1년 전
        } else if (month == 100) {
            day = day.minusYears(10); // 전체 기간 조회를 위한 과거 날짜
        } else {
            return null;
        }
        // StoredProcedureQuery 객체 생성

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("youtube_view")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_Start_Date", Date.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId)
                .setParameter("in_Start_Date", Date.valueOf(day));

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            return null;
        }

        // 결과 처리
        List<ViewDto> viewList = new ArrayList<>();
        for (Object[] result : resultList) {
            ViewDto viewDto = new ViewDto(
                    result[0].toString(),  // 조회수
                    result[1].toString()   // 조회날짜
            );
            viewList.add(viewDto);
        }

        return viewList;
    }

    // 검색량
    public List<SearchDto> findSearch(String productId, int month) {
        // 검색량 (3개월, 6개월, 1년, 전체)
        // 현재 날짜 기준으로 날짜 계산
        LocalDate day = LocalDate.now();

        if (month == 3) {
            day = day.minusMonths(month); // 3개월 전
        } else if (month == 6) {
            day = day.minusMonths(month); // 6개월 전
        } else if (month == 12) {
            day = day.minusMonths(month); // 1년 전
        } else if (month == 100) {
            day = day.minusYears(10); // 전체 기간 조회를 위한 과거 날짜
        } else {
            return null;
        }

        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("product_search")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_Start_Date", Date.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId)
                .setParameter("in_Start_Date", Date.valueOf(day));

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            return null;
        }

        // 결과 처리
        List<SearchDto> searchList = new ArrayList<>();
        for (Object[] result : resultList) {
            SearchDto searchDto = new SearchDto(
                    result[0].toString(),  // 검색량
                    result[1].toString()   // 검색날짜
            );
            searchList.add(searchDto);
        }

        return searchList;
    }

    // 스트리밍 수
    public MaxAndMinDto findStreaming(String productId, int month) {
        // 스트리밍 수 (3개월, 6개월, 1년, 전체)
        // 현재 날짜 기준으로 날짜 계산
        LocalDate day = LocalDate.now();

        if (month == 3) {
            day = day.minusMonths(month); // 3개월 전
        } else if (month == 6) {
            day = day.minusMonths(month); // 6개월 전
        } else if (month == 12) {
            day = day.minusMonths(month); // 1년 전
        } else if (month == 100) {
            day = day.minusYears(15); // 전체 기간 조회를 위한 과거 날짜
        } else {
            return null;
        }

        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("melon_streaming")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("in_Start_Date", Date.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId)
                .setParameter("in_Start_Date", Date.valueOf(day));

        // 프로시저 실행 및 결과 조회
        List<Object[]> resultList = query.getResultList();
        int max = (Integer) resultList.get(0)[0];
        int min = (Integer) resultList.get(0)[0];
        // 결과가 없을 경우 null 반환
        if (resultList.isEmpty()) {
            return null;
        }

        // 결과 처리
        List<SteamingDto> streamingList = new ArrayList<>();
        for (Object[] result : resultList) {
            SteamingDto steamingDto = new SteamingDto(
                    (Integer) result[0],  // 스트리밍수
                    result[1].toString()   // 스트리밍날짜
            );
            if (steamingDto.getValue() > max) max = steamingDto.getValue();
            if (steamingDto.getValue() < min) min = steamingDto.getValue();
            streamingList.add(steamingDto);
        }

        return new MaxAndMinDto(streamingList, max, min);
    }

    // 공연일정
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
            return null;
        }

        // 결과 처리
        List<ConsertDto> consertList = new ArrayList<>();
        for (Object[] result : resultList) {
            ConsertDto consertDto = new ConsertDto(
                    result[0].toString(),  // title
                    (String) result[1],  // place
                    (String) result[2],  // period
                    (String) result[3],  // imageUrl
                    (String) result[4]   // link
            );
            consertList.add(consertDto);
        }

        return consertList;
    }
}
