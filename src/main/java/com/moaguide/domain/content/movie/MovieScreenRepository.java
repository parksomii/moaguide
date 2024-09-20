package com.moaguide.domain.content.movie;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieScreenRepository extends JpaRepository<MovieScreen, Long> {

    @Query("select ms.screenCount FROM MovieScreen ms where ms.productId.productId =:Id order by ms.day")
    List<Integer> findByProductId(@Param("Id") String productId);

    @Query("select ms.screenCount FROM MovieScreen ms where ms.productId.productId =:Id order by ms.day")
    List<Integer> findByProductIdTop10(@Param("Id") String productId, Pageable pageable);
}
