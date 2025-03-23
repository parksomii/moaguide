package com.moaguide.service;

import com.moaguide.refactor.product.repository.CategoryContentRepository;
import com.moaguide.dto.CategoryDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {

    private final CategoryContentRepository categoryContentRepository;

    public List<CategoryDto> getAllCategories() {
        return categoryContentRepository.findAll()
                .stream()
                .map(category -> new CategoryDto(
                        category.getCategoryId(),
                        category.getName(),
                        category.getDescription()
                ))
                .collect(Collectors.toList());
    }
}
