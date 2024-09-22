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

    public ContentPublishDto findPublish(String productId) {
            log.info("findPublish 호출됨, productId: {}", productId);

            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("GetContentpublish");

            // 파라미터 설정 로그
            log.info("프로시저 파라미터 설정: pro_Id = {}", productId);
            query.registerStoredProcedureParameter("pro_Id", String.class, ParameterMode.IN);
            query.setParameter("pro_Id", productId);

            List<Object[]> resultList = query.getResultList();

            // 결과 리스트 로그
            log.info("프로시저 호출 결과 리스트 사이즈: {}", resultList.size());

            if (resultList.isEmpty()) {
                log.warn("결과가 없습니다.");
                return null;
            }

            // 첫 번째 결과 로그
            Object[] result = resultList.get(0);
            log.info("첫 번째 결과: {}", (Object) result);

            // 결과를 DTO로 변환하는 과정에서 로그
            ContentPublishDto contentPublishDto = new ContentPublishDto();
            contentPublishDto.setName((String) result[0]);
            contentPublishDto.setGenre((String) result[1]);
            contentPublishDto.setType((String) result[2]);
            contentPublishDto.setMinAmount((Long) result[3]);
            contentPublishDto.setMaxAmount((Long) result[4]);
            contentPublishDto.setPiece((Integer) result[5]);
            contentPublishDto.setBasePrice((Integer) result[6]);
            contentPublishDto.setMinInvestment((Integer) result[7]);
            contentPublishDto.setIssuanceDate((Date) result[8]);
            contentPublishDto.setExpirationDate((Date) result[9]);

            log.info("ContentPublishDto 생성 완료: {}", contentPublishDto);

            return contentPublishDto;
    }
}
