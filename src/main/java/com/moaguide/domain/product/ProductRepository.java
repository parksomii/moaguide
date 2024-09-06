package com.moaguide.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT p from Product p where p.productId = :productId")
    Optional<Product> findById(@RequestParam("productId") String productId);
}