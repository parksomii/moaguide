package com.moaguide.domain.view;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, Long> {

}
