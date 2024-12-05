package com.moaguide.service;

import com.moaguide.domain.card.Card;
import com.moaguide.domain.card.CardRepository;
import com.moaguide.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    public Card findByNickanme(String nickname) {
        return cardRepository.findBynickname(nickname).orElse(null);
    }

    public void save(String nickname, String cardCompany, Integer cardNumber) throws DuplicateKeyException {
        cardRepository.save(new Card(nickname,cardCompany,cardNumber));
    }

    public void delete(String nickname) {
        cardRepository.deleteByNickname(nickname);
    }
}
