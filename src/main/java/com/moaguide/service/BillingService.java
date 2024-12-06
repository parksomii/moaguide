package com.moaguide.service;

import com.moaguide.domain.billding.BillingInfo;
import com.moaguide.domain.billding.BillingInfoRepository;
import com.moaguide.domain.card.Card;
import com.moaguide.domain.card.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
@AllArgsConstructor
public class BillingService {
    private final BillingInfoRepository billingInfoRepository;
    private final CardRepository cardRepository;

    @Transactional(rollbackFor = Exception.class)
    public void delete(String nickname) throws Exception{
        cardRepository.deleteByNickname(nickname);
        billingInfoRepository.deleteByNickname(nickname);
    }

    public Card findByNickanme(String nickname) {
        return cardRepository.findBynickname(nickname).orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(String nickname, String cardCompany, Integer cardNumber, String customerKey, String billingKey) throws Exception{
        int cardline = cardRepository.update(nickname,cardCompany,cardNumber);
        int billingline = billingInfoRepository.update(customerKey,billingKey,nickname,new Date(System.currentTimeMillis()));
        if(billingline == 0){
            throw new Exception();
        }
        if(cardline == 0){
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(String nickname, String cardCompany, Integer cardNumber, String customerKey, String billingKey) throws DuplicateKeyException{
        billingInfoRepository.save(new BillingInfo(customerKey, billingKey,nickname,new Date(System.currentTimeMillis())));
        cardRepository.save(new Card(nickname,cardCompany,cardNumber));
    }
}
