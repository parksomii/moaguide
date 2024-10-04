package com.moaguide.service.view;

import com.moaguide.domain.news.News;
import com.moaguide.domain.view.NewsView;
import com.moaguide.domain.view.NewsViewId;
import com.moaguide.domain.view.NewsViewRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class NewsViewService {
    private final NewsViewRepository newsViewRepository;

    @Transactional
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
