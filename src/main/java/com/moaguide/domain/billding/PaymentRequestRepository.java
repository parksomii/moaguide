package com.moaguide.domain.billding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest,String> {

    @Query("delete FROM PaymentRequest p where p.nickname =:nickname")
    void deletebyNickname(@Param("nickname") String nickname);
}
