package com.moaguide.refactor.article.repository;


import com.moaguide.refactor.article.entity.PdfList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfListRepository extends JpaRepository<PdfList, String> {
}
