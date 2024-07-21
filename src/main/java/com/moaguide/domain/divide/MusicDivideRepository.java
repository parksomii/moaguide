package com.moaguide.domain.divide;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MusicDivideRepository extends JpaRepository<MusicDivide, Long> {


    @Procedure(procedureName="MusiconeDivide")
    MusicDivide findByProductIdANDONE(String id);
}
