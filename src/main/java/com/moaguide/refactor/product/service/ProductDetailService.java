package com.moaguide.refactor.product.service;


import static com.moaguide.refactor.util.EmptyCheckUtil.isListEmpty;

import com.moaguide.refactor.art.dto.ArtDetailDto;
import com.moaguide.refactor.building.dto.BuildingReponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductDetailService {
	private final EntityManager entityManager;

	public ResponseEntity<Object> buildingDetail(String productId, String nickname) {
		StoredProcedureQuery query = entityManager
			.createStoredProcedureQuery("building_detail")
			.registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
			.registerStoredProcedureParameter("nickname", String.class, ParameterMode.IN)
			.setParameter("in_Product_Id", productId)
			.setParameter("nickname", nickname);

		// 프로시저 실행
		List<Object[]> resultList = query.getResultList();

		if (isListEmpty(resultList)) {
			ResponseEntity.ok(new HashMap<>());
		}

		Object[] result = resultList.get(0);  // 첫 번째 결과만 사용


		BuildingReponseDto building = new BuildingReponseDto(
			(String) result[0],
			(String) result[1],
			()
		)
	}

	public ResponseEntity<Object> musicDetail(String productId, String nickname) {
	}

	public ResponseEntity<Object> contentsDetail(String productId, String nickname) {
	}

	public ResponseEntity<Object> artDetail(String productId, String nickname) {
		StoredProcedureQuery query = entityManager
			.createStoredProcedureQuery("art_details")
			.registerStoredProcedureParameter("in_Product_Id", String.class, ParameterMode.IN)
			.registerStoredProcedureParameter("nickname", String.class, ParameterMode.IN)
			.setParameter("in_Product_Id", productId)
			.setParameter("nickname", nickname);

		// 프로시저 실행
		List<Object[]> resultList = query.getResultList();

		// 결과가 없을 경우 null 리턴
		if (isListEmpty(resultList)) {
			ResponseEntity.ok(new HashMap<>());
		}

		// 결과값을 DTO에 매핑
		Object[] result = resultList.get(0);  // 첫 번째 결과만 사용

		// ArtDetailDto 매핑
		 ArtDetailDto art= new ArtDetailDto(
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
		 return ResponseEntity.ok().body(art);
	}

	public ResponseEntity<Object> cowDetail(String productId, String nickname) {
	}
}
