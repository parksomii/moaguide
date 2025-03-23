package com.moaguide.service.view;

import com.moaguide.refactor.product.entity.ProductView;
import com.moaguide.refactor.product.repository.ProductViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@AllArgsConstructor
public class ProductViewService {
    private ProductViewRepository productViewRepository;

    public void insert(String productId, String nickname) {
        // 현재 시간을 가져옴
        Timestamp currentTime = Timestamp.from(Instant.now());

        // ProductView 엔티티 생성 및 값 설정
        ProductView productView = new ProductView(nickname, productId, currentTime);

        // JPA의 save 메서드를 사용해 엔티티 저장
        productViewRepository.save(productView);
    }
}
