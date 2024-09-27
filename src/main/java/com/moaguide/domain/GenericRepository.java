package com.moaguide.domain;

import com.moaguide.dto.NewDto.customDto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
@Slf4j
public class GenericRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SummaryCustomDto> findlistBookmark(int page, int size, String sort, String nickname) {
        return entityManager.createNativeQuery("CALL list(:page, :size, :sort, :nickname)", "SummaryCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("sort", sort)
                .setParameter("nickname", nickname)
                .getResultList();
    }


    @Transactional
    public List<SummaryCustomDto> findCustomList(int page, int size, String sort) {
        return entityManager.createNativeQuery("CALL list(:page, :size, :sort)", "SummaryCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("sort", sort)
                .getResultList();
    }

    public List<SummaryCustomDto> findCustomListCategory(int page, int size, String sort, String category) {
        // 프로시저를 호출하는 네이티브 쿼리
        return entityManager.createNativeQuery("CALL list_category(:page, :size, :sort, :category)", "SummaryCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("sort", sort)
                .setParameter("category", category)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomIssue(int page, int size, Date sqlDate) {
        // 프로시저를 호출하는 네이티브 쿼리
        return entityManager.createNativeQuery("CALL Issue(:page, :size, :day)", "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomStart(int page, int size ,Date sqlDate) {
        return entityManager.createNativeQuery("CALL start(:page, :size, :day)", "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomIssueCategory(int page, int size, Date sqlDate, String category) {
        return entityManager.createNativeQuery("CALL Issue_category(:page, :size, :day, :category)", "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .setParameter("category", category)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomStartCategory(int page, int size, Date sqlDate, String category) {
        return entityManager.createNativeQuery("CALL start_category(:page, :size, :day, :category)","IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .setParameter("category", category)
                .getResultList();
    }

    public List<finishCustomDto> findfinish(int page, int size) {
        String sql = "CALL finish(:page, :size)";
        return entityManager.createNativeQuery(sql, "SaleCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .getResultList();
    }

    public List<endCustomDto> findend(int page, int size) {
        String sql = "CALL endlist(:page, :size)";
        return entityManager.createNativeQuery(sql, "endCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .getResultList();
    }

    public List<finishCustomDto> findfinishCategory(int page, int size, String category) {
        String sql = "CALL finish_category(:page, :size, :day, :category)";
        return entityManager.createNativeQuery(sql, "SaleCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("category", category)
                .getResultList();
    }

    public List<endCustomDto> findendCategory(int page, int size, String category) {
        String sql = "CALL endlist_category(:page, :size, :day, :category)";
        return entityManager.createNativeQuery(sql, "endCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("category", category)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomIssuebyBookmark(int page, int size, Date sqlDate, String nickname) {
        return entityManager.createNativeQuery("CALL Issue_bookmark(:page, :size, :day, :nickname)", "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomStartBookmark(int page, int size, Date sqlDate, String nickname) {
        return entityManager.createNativeQuery("CALL start_bookmark(:page, :size, :day, :nickname)", "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .setParameter("nickname", nickname)
                .getResultList();
    }

//    public List<MovieScheduleDto> findByschedule(String productId) {
//        return entityManager.createNativeQuery("call GetMoviesInDateRange(movieId)", "MovieScheduleMapping")
//                .setParameter("movieId",productId)
//                .getResultList();
//    }
}
