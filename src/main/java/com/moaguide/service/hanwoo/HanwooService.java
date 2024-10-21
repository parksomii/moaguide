package com.moaguide.service.hanwoo;

import com.moaguide.domain.detail.HanwooDetailRepository;
import com.moaguide.dto.NewDto.HanwooBaseResponseDto;
import com.moaguide.dto.NewDto.HanwooDetailDto;
import com.moaguide.dto.NewDto.customDto.HanwooFarmDto;
import com.moaguide.dto.NewDto.customDto.HanwooPublishDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@Slf4j
@Service
public class HanwooService {
    private final HanwooDetailRepository hanwooRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public HanwooDetailDto findHanwooDetail(String productId, String nickname) {
        // 프로시저 호출
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("hanwoo_detail")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("nickname", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId)
                .setParameter("nickname", nickname);

        // 결과 받아오기
        Object[] result = (Object[]) query.getSingleResult();

        // HanwooDetailDto 생성 및 반환
        return new HanwooDetailDto(
                (String) result[0],  // productId
                (String) result[1],  // category
                (String) result[2],  // platform
                (String) result[3],  // title
                (String) result[4],  // name
                ((Long) result[5]),  // recruitmentPrice (Integer 그대로 사용)
                ((BigDecimal) result[6]).doubleValue(),  // recruitmentRate (Double 그대로 사용)
                ((Long) result[7]),  // totalPrice
                ((Date) result[8]).toLocalDate(),  // paymentDate
                ((Integer) result[9]),  // minInvestment
                (String) result[10],
                ((Long) result[11]) == 1 // isBookmarked: Long 값을 Boolean으로 변환
        );
    }

    public HanwooBaseResponseDto findDetail(String productId) {
        // 프로시저 호출
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("hanwoo_base")
                .registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
                .setParameter("in_Product_Id", productId);

        // 결과 받아오기
        Object[] result = (Object[]) query.getSingleResult();

        // HanwooPublishDto 생성
        HanwooPublishDto publishDto = new HanwooPublishDto(
                (String) result[0],  // name
                (String) result[1],  // type
                ((Integer) result[2]),  // piece (Integer 그대로 사용)
                ((Integer) result[3]),  // basePrice (Integer -> Long 변환)
                String.valueOf(result[4]),  // totalPrice
                (String) result[5],  // recruitingType
                (String) result[6],  // rightsStructure
                (String) result[7],  // revenueStructure
                ((Date) result[8]).toLocalDate(),  // paymentDate
                ((Date) result[9]).toLocalDate(),  // subscriptionDate
                ((Date) result[10]).toLocalDate(), // allocationDate
                result[11] == null ? null :((Date) result[11]).toLocalDate() // criteriaDate
        );

        // HanwooFarmDto 생성
        HanwooFarmDto farmDto = new HanwooFarmDto(
                (String) result[12],  // certificationNumber
                (String) result[13],  // certificationAgency
                (String) result[14],  // manager
                String.valueOf(result[15]),  // certifiedHeads
                (String) result[16],  // cattleBreed
                result[17]  == null ? null :((Date) result[17]).toLocalDate() // criteriaDate
        );

        // HanwooBaseResponseDto 생성 및 반환
        return new HanwooBaseResponseDto(publishDto, farmDto);
    }
}