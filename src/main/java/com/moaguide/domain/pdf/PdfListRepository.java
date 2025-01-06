package com.moaguide.domain.pdf;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfListRepository extends JpaRepository<PdfList, String> {
}
