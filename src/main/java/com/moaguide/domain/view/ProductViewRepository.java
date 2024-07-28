package com.moaguide.domain.view;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, Long> {

}
