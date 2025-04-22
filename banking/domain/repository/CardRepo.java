package banking.domain.repository;

import banking.domain.model.Card;

public interface CardRepo {
    void saveCard(Card card);
    Card getCard(String cardNumber);
}
