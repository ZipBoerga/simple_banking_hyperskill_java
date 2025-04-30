package banking.core.domain.repository;

import banking.core.domain.model.Card;

public interface CardRepo {
    void saveCard(Card card);
    Card getCard(String cardNumber);
    Card[] getCards();
    void topUpBalance(String cardNumber, int income);
    void transfer(String fromCardNumber, String toCardNumber, int amount);
    void deleteCard(String cardNumber);
}
