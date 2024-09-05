package com.moaguide.service.view;

import com.moaguide.domain.summary.Product;
import com.moaguide.domain.view.ProductView;
import com.moaguide.domain.view.ProductViewId;
import com.moaguide.domain.view.ProductViewRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ProductViewService {
    private ProductViewRepository productViewRepository;

    public String insert(Product productId, String localStorageKey, String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            productViewRepository.save(new ProductView(new ProductViewId(localStorageKey, localDate, productId)));
            return "User saved successfully";
        } catch (DataIntegrityViolationException e) {
            return "Primary key already exists. User not saved.";
        }
    }
}
