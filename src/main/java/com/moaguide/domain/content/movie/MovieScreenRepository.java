package com.moaguide.domain.content.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieScreenRepository extends JpaRepository<MovieScreen, Long> {

    @Query("select ms.screenCount FROM MovieScreen ms where ms.productId.productId =:Id")
    List<Integer> findByProductId(@Param("Id") String productId);
}
