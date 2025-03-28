package com.moaguide.refactor.payments.repository;

import com.moaguide.refactor.payments.dto.PaymentDto;
import com.moaguide.refactor.payments.entity.LocalPaymentRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public interface LocalPaymentRequestRepository extends JpaRepository<LocalPaymentRequest, String> {

	@Modifying
	@Query("delete FROM LocalPaymentRequest p where p.nickname =:nickname")
	void deletebyNickname(@Param("nickname") String nickname);

	@Query("select p.nickname FROM LocalPaymentRequest p where p.NextPaymentDate =:nowDate")
	List<String> findByDate(@Param("nowDate") LocalDateTime nowDate);

	@Modifying
	@Query("delete FROM LocalPaymentRequest p where p.nickname =:nickname and p.NextPaymentDate< :enddate")
	void deletebyNicknameAndDate(@Param("nickname") String nickname,
		@Param("enddate") LocalDateTime enddate);

	@Query("select new com.moaguide.refactor.payments.dto.PaymentDto(p.orderId,b.billingKey,b.customerKey,p.amount,p.nickname,p.failCount) FROM LocalPaymentRequest p left join BillingInfo b on p.nickname = b.nickname where p.NextPaymentDate =:nowDate")
	List<PaymentDto> findByNextPaymentDate(@Param("nowDate") LocalDateTime nowDate);

	@Modifying
	@Query("update LocalPaymentRequest p set p.failCount=p.failCount+1,p.NextPaymentDate=:date where p.nickname =:nickname and p.NextPaymentDate =:nowdate")
	void updatefailList(@Param("nickname") String nickname, @Param("date") LocalDateTime date,
		@Param("nowdate") LocalDateTime nowdate);

	@Query("select p.nickname fROM LocalPaymentRequest p where p.failCount>=5 or p.NextPaymentDate<:endDate")
	List<String> findByFailCount(@Param("endDate") LocalDateTime date);

	@Modifying
	@Query("DELETE FROM LocalPaymentRequest p WHERE p.nickname IN :nicknames")
	void deleteByFailCount(@Param("nicknames") List<String> nickname);

}
