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

    @Query("SELECT new com.moaguide.dto.NewDto.BusinessAreaDto( b.cbd, b.cbdDistance , b.cbdCar , b.cbdSubway, b.gbd , b.gbdDistance , b.cbdCar , b.cbdSubway, b.ybd, b.ybdDistance, b.ybdCar, b.ybdSubway,n.node,n.line ) " +
            "FROM BusinessArea b,BuildingDetail bd, NearBus n " +
            "WHERE bd.productId.productId =  b.productId.productId and bd.keyword = n.keyword and " +
            "b.productId.productId = :id ")
    BusinessAreaDto findByproductId(@Param("id") String product_Id);
}
