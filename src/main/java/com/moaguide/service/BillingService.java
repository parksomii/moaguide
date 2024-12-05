package com.moaguide.service;

import com.moaguide.domain.billding.BillingInfo;
import com.moaguide.domain.billding.BillingInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@AllArgsConstructor
public class BillingService {
    private final BillingInfoRepository billingInfoRepository;

    public void save(String nickname, String customerKey, String billingKey) throws DuplicateKeyException {
        billingInfoRepository.save(new BillingInfo(customerKey, billingKey,nickname,new Date(System.currentTimeMillis())));
    }

    public void delete(String nickname) {
        billingInfoRepository.deleteByNickname(nickname);
    }
}
