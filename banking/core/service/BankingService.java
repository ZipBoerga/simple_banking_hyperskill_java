package banking.core.service;

import banking.domain.model.Card;

public interface BankingService {
    Card createCard();
    Card login(String cardNumber, String pin);
}
