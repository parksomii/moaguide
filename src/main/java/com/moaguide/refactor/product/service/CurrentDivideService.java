package com.moaguide.refactor.product.service;

import com.moaguide.refactor.product.repository.CurrentDivideRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrentDivideService {
    private final CurrentDivideRepository currentDivideRepository;

    public Integer findCycle(String productId) {
        Integer cycle = currentDivideRepository.findCycle(productId);
        return cycle;
    }

}
