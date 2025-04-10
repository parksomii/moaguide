package com.moaguide.refactor.contents.repository;


import com.moaguide.refactor.contents.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

}
