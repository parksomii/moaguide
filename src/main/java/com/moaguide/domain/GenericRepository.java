package com.moaguide.domain;

import com.moaguide.dto.NewDto.customDto.IssueCustomDto;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.NewDto.customDto.finishCustomDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public class GenericRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<SummaryCustomDto> findCustomList(int page, int size, String sort) {
        // 프로시저를 호출하는 네이티브 쿼리
        String sql = "CALL list(:page, :size, :sort)";
        return entityManager.createNativeQuery(sql, SummaryCustomDto.class)
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("sort", sort)
                .getResultList();
    }

    public List<SummaryCustomDto> findCustomListCategory(int page, int size, String sort, String category) {
        // 프로시저를 호출하는 네이티브 쿼리
        String sql = "CALL list_category(:page, :size, :sort, :category)";
        return entityManager.createNativeQuery(sql, "SummaryCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("sort", sort)
                .setParameter("category", category)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomIssue(int page, int size, Date sqlDate) {
        // 프로시저를 호출하는 네이티브 쿼리
        String sql = "CALL Issue(:page, :size, :day)";
        return entityManager.createNativeQuery(sql, "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomStart(int page, int size ,Date sqlDate) {
        String sql = "CALL start(:page, :size, :day)";
        return entityManager.createNativeQuery(sql, "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomIssueCategory(int page, int size, Date sqlDate, String category) {
        String sql = "CALL Issue_category(:page, :size, :day, :category)";
        return entityManager.createNativeQuery(sql, "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .setParameter("category", category)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomStartCategory(int page, int size, Date sqlDate, String category) {
        String sql = "CALL start_category(:page, :size)";
        return entityManager.createNativeQuery(sql, "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .setParameter("category", category)
                .getResultList();
    }

    public List<finishCustomDto> findfinish(int page, int size) {
        String sql = "CALL finsh(:page, :size)";
        return entityManager.createNativeQuery(sql, "finishCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .getResultList();
    }

    public List<finishCustomDto> findend(int page, int size) {
        String sql = "CALL endlist(:page, :size)";
        return entityManager.createNativeQuery(sql, "finishCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .getResultList();
    }

    public List<finishCustomDto> findfinishCategory(int page, int size, String category) {
        String sql = "CALL finsh_category(:page, :size, :day, :category)";
        return entityManager.createNativeQuery(sql, "finishCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("category", category)
                .getResultList();
    }

    public List<finishCustomDto> findendCategory(int page, int size, String category) {
        String sql = "CALL endlist_category(:page, :size, :day, :category)";
        return entityManager.createNativeQuery(sql, "finishCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("category", category)
                .getResultList();
    }

}
