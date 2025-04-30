package banking.core.domain.repository;

import banking.core.domain.model.Card;

public interface CardRepo {
    void saveCard(Card card);
    Card getCard(String cardNumber);
    Card[] getCards();
}
