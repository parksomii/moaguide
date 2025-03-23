package com.moaguide.refactor.product.repository;

import com.moaguide.dto.NewDto.customDto.IssueCustomDto;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.NewDto.customDto.endCustomDto;
import com.moaguide.dto.NewDto.customDto.finishCustomDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
        return entityManager.createNativeQuery("CALL list_bookmark(:page, :size, :sort, :nickname)", "SummaryCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("sort", sort)
                .setParameter("nickname", nickname)
                .getResultList();
    }


    @Transactional
    public List<SummaryCustomDto> findCustomList(int page, int size, String sort, String nickname) {
        return entityManager.createNativeQuery("CALL list(:page, :size, :sort,:nickname)", "SummaryCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("sort", sort)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<SummaryCustomDto> findCustomListCategory(int page, int size, String sort, String category, String nickname)  {
        // 프로시저를 호출하는 네이티브 쿼리
        return entityManager.createNativeQuery("CALL list_category(:page, :size, :sort, :category,:nickname)", "SummaryCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("sort", sort)
                .setParameter("category", category)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomIssue(int page, int size, Date sqlDate,String nickname) {
        // 프로시저를 호출하는 네이티브 쿼리
        return entityManager.createNativeQuery("CALL Issue(:page, :size, :day,:nickname)", "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomStart(int page, int size ,Date sqlDate,String nickname) {
        return entityManager.createNativeQuery("CALL start(:page, :size, :day,:nickname)", "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomIssueCategory(int page, int size, Date sqlDate, String category, String nickname) {
        return entityManager.createNativeQuery("CALL Issue_category(:page, :size, :day, :category,:nickname)", "IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .setParameter("category", category)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<IssueCustomDto> findCustomStartCategory(int page, int size, Date sqlDate, String category,String nickname) {
        return entityManager.createNativeQuery("CALL start_category(:page, :size, :day, :category,:nickname)","IssueCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("day", sqlDate)
                .setParameter("category", category)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<finishCustomDto> findfinish(int page, int size, String nickname) {
        String sql = "CALL finish(:page, :size,:nickname)";
        return entityManager.createNativeQuery(sql, "SaleCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<endCustomDto> findend(int page, int size, String nickname) {
        String sql = "CALL endlist(:page, :size,:nickname)";
        return entityManager.createNativeQuery(sql, "endCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<finishCustomDto> findfinishCategory(int page, int size, String category, String nickname) {
        String sql = "CALL finish_category(:page, :size, :category,:nickname)";
        return entityManager.createNativeQuery(sql, "SaleCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("category", category)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<endCustomDto> findendCategory(int page, int size, String category, String nickname) {
        String sql = "CALL endlist_category(:page, :size, :category,:nickname)";
        return entityManager.createNativeQuery(sql, "endCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("category", category)
                .setParameter("nickname", nickname)
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

    public List<endCustomDto> findendBookmark(int page, int size, String nickname) {
        String sql = "CALL endlist_bookmark(:page, :size, :nickname)";
        return entityManager.createNativeQuery(sql, "endCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<endCustomDto> findfinishBookmark(int page, int size, String nickname) {
        String sql = "CALL finish_bookmark(:page, :size, :nickname)";
        return entityManager.createNativeQuery(sql, "SaleCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public int getStartCount(Date sqlDate) {
        String sql = "CALL startCount(:day)";
        // 프로시저 호출 및 Integer로 결과 받기
        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter("day", sqlDate)
                .getSingleResult()).intValue();
    }

    public int getStartCountBookmark(Date sqlDate, String nickname) {
        String sql = "CALL startCountBookmark(:day,:nickname)";
        // 프로시저 호출 및 Integer로 결과 받기
        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter("day", sqlDate)
                .setParameter("nickname", nickname)
                .getSingleResult()).intValue();
    }

    public int getStartCountCategory(Date sqlDate, String category) {
        String sql = "CALL startCountCategory(:day,:category)";
        // 프로시저 호출 및 Integer로 결과 받기
        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter("day", sqlDate)
                .setParameter("category", category)
                .getSingleResult()).intValue();
    }

    public int findissuebyCategroy(Date sqlDate, String category) {
        String sql = "CALL Issue_count_category(:day,:category)";
        // 프로시저 호출 및 Integer로 결과 받기
        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter("day", sqlDate)
                .setParameter("category", category)
                .getSingleResult()).intValue();
    }

    public List<SummaryCustomDto> findHometCategory(String category, String nickname) {
        String sql = "Call home_Category(:category,:nickname)";
        return entityManager.createNativeQuery(sql, "SummaryCustomDtoMapping")
                .setParameter("category", category)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    public List<SummaryCustomDto> funble(int page, int size, String sort, String nickname)  {
        // 프로시저를 호출하는 네이티브 쿼리
        return entityManager.createNativeQuery("CALL funble(:page, :size, :sort,:nickname)", "SummaryCustomDtoMapping")
                .setParameter("page", page)
                .setParameter("size", size)
                .setParameter("sort", sort)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
