package com.moaguide.refactor.news.repository;

import com.moaguide.refactor.news.entity.NewsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsViewRepository extends JpaRepository<NewsView, Long> {

}
