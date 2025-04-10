package com.moaguide.refactor.payments.repository;

import com.moaguide.refactor.payments.dto.lastLogDto;
import com.moaguide.refactor.payments.entity.PaymentLog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentLogRepository extends JpaRepository<PaymentLog, Long> {

	@Query("select p FROM PaymentLog  p where p.nickname=:nickname")
	List<PaymentLog> findAll(@Param("nickname") String nickname);

	@Modifying
	@Query("delete FROM PaymentLog p where p.nickname=:nickname")
	void deleteByNickname(@Param("nickname") String nickname);

	@Query("select p.orderId FROM PaymentLog p where p.nickname=:nickname order by  p.id desc")
	Page<String> findLog(@Param("nickname") String nickname, Pageable page);

	@Query("select new com.moaguide.refactor.payments.dto.lastLogDto(p.orderName,CASE WHEN p.totalAmount = 0 THEN p.discount ELSE p.totalAmount END) FROM PaymentLog p where p.nickname=:nickname order by  p.id desc")
	Page<lastLogDto> findlastLog(String nickname, Pageable page);

}
