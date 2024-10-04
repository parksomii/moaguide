package com.moaguide.domain.divide;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicDivideRepository extends JpaRepository<MusicDivide, Long> {


    @Procedure(procedureName="MusiconeDivide")
    MusicDivide findByProductIdANDONE(String id);
}
