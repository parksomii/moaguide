package com.moaguide.refactor.product.repository;

import com.moaguide.refactor.product.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform,Integer> {

}
