package banking.core.application.service;

import banking.core.domain.model.Card;

public interface BankingService {
    Card createCard();
    Card login(String cardNumber, String pin);
}
