package com.moaguide.service.view;

import com.moaguide.domain.news.News;
import com.moaguide.domain.view.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class NewsViewService {
    NewsViewRepository newsViewRepository;

    public String insert(News news, String localStorageKey, String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            newsViewRepository.save(new NewsView(new NewsViewId(localStorageKey, localDate, news)));
            return "User saved successfully";
        } catch (DataIntegrityViolationException e) {
            return "Primary key already exists. User not saved.";
        }
    }
}
