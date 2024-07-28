package com.moaguide.domain.view;

import com.moaguide.domain.news.News;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "News_View")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsView {
    @EmbeddedId
    private NewsViewId id;
}
