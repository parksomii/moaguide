package com.moaguide.domain.building.businessarea;


import com.moaguide.dto.NewDto.BusinessAreaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BusinessAreaRepository extends JpaRepository<BusinessArea, Long> {

    @Procedure(name = "business")
    BusinessAreaDto findByproductId(@Param("id") String product_Id);
}
