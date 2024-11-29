package com.moaguide.service.art;

import com.moaguide.domain.art.ArtAuthorRepository;
import com.moaguide.domain.detail.ArtDetailRepository;
import com.moaguide.dto.NewDto.ArtBaseResponseDto;
import com.moaguide.dto.NewDto.ArtDetailDto;
import com.moaguide.dto.NewDto.customDto.ArtAuthorDto;
import com.moaguide.dto.NewDto.customDto.ArtPublishDto;
import com.moaguide.dto.NewDto.customDto.ArtWorkDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class ArtService {
    @PersistenceContext
    private final EntityManager entityManager;

    public ArtDetailDto findArtDetail(String productId, String nickname) {
        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("art_details")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("nickname", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId)
                .setParameter("nickname", nickname);


        // 프로시저 실행
        List<Object[]> resultList = query.getResultList();

        // 결과가 없을 경우 null 리턴
        if (resultList.isEmpty()) {
            return null;
        }

        // 결과값을 DTO에 매핑
        Object[] result = resultList.get(0);  // 첫 번째 결과만 사용

        // ArtDetailDto 매핑
        return new ArtDetailDto(
                (String) result[0],  // productId
                (String) result[1],  // category
                (String) result[2],  // platform
                (String) result[3],  // name
                (String) result[4],  // authorName
                ((Long) result[5]),  // recruitmentPrice (Integer 그대로 사용)
                ((BigDecimal) result[6]).doubleValue(),  // recruitmentRate (Double 그대로 사용)
                ((Long) result[7]),  // totalPrice
                String.valueOf(result[8]),  // subscriptionDate
                ((Integer) result[9]),  // minInvestment (Integer 그대로 사용)
                (String) result[10],
                ((Long) result[11]) == 1 // isBookmarked: Long 값을 Boolean으로 변환
        );
    }

    public ArtBaseResponseDto findArtBase(String productId) {
        // StoredProcedureQuery 객체 생성
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("art_bases");

        // IN 파라미터 등록
        storedProcedure.registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN);
        storedProcedure.setParameter("in_Product_Id", productId);

        // 프로시저 실행
        List<Object[]> resultList = storedProcedure.getResultList();

        // 결과가 없을 경우 null 리턴
        if (resultList.isEmpty()) {
            return null;
        }

        // 결과값을 DTO에 매핑
        Object[] result = resultList.get(0);  // 첫 번째 결과만 사용

        // ArtPublishDto 매핑
        ArtPublishDto artPublishDto = new ArtPublishDto(
                (String) result[0],  // product_name
                result[1] == null ? "" : (String) result[1],  // publisher -> type
                (String) result[2], // author_name
                ((Integer) result[3]),  // piece (Integer 그대로 사용)
                ((Integer) result[4]),  // basePrice (Integer -> Long 변환)
                String.valueOf(result[5]),  // totalPrice
                ((Date) result[6]).toLocalDate(),  // issuanceDate
                String.valueOf(result[17])
        );

        // ArtAuthorDto 매핑
        ArtAuthorDto artAuthorDto = new ArtAuthorDto(
                (String) result[2], // author_name
                result[7] == null ? "" : (String) result[7], // nationality
                result[8] == null ? "" : ((Date) result[8]).toLocalDate().toString(), // birth
                result[9] == null ? "" : (String) result[9], // academic_ability
                result[10] == null ? "" : (String) result[10], // award_career
                result[11] == null ? "" : (String) result[11], // major
                result[12] == null ? "" : (String) result[12]  // introduction
        );

        // ArtWorkDto 매핑
        ArtWorkDto artWorkDto = new ArtWorkDto(
                (String) result[13],  // name
                (String) result[14],  // size
                (String) result[15],  // material
                (String) result[16]   // productionDate
        );

        // 최종적으로 ArtBaseResponseDto 리턴
        return new ArtBaseResponseDto(artPublishDto, artAuthorDto, artWorkDto);
    }
}