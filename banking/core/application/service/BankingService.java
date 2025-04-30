package banking.core.application.service;

import banking.core.domain.model.Card;
import banking.core.domain.repository.CardRepo;

public class BankingService {

    private final CardRepo cardRepo;
    private final CardGeneratorService cardGeneratorService;

    public BankingService(CardRepo cardRepo, CardGeneratorService cardGeneratorService) {
        this.cardRepo = cardRepo;
        this.cardGeneratorService = cardGeneratorService;
    }

    public Card createCard() {
        final Card card = cardGeneratorService.generateCard();
        cardRepo.saveCard(card);
        return card;
    }

    public Card login(String cardNumber, String pin) {
        Card card = cardRepo.getCard(cardNumber);
        if (card != null && card.getPin().equals(pin)) {
            return card;
        } else {
            return null;
        }
    }

    public Card addIncome(String cardNumber, int income) {
        cardRepo.topUpBalance(cardNumber, income);
        return cardRepo.getCard(cardNumber);
    }

    // lowkey lacks checks on if DB didn't fail, but redundant for not
    public Card transfer(String fromCardNumber, String toCardNumber, int amount) {
        cardRepo.transfer(fromCardNumber, toCardNumber, amount);
        return cardRepo.getCard(fromCardNumber);
    }

    public void deleteCard(String cardNumber) {
        cardRepo.deleteCard(cardNumber);
    }

    public boolean isCardExist(String cardNumber) {
        return cardRepo.getCard(cardNumber) != null;
    }

    public boolean isValidCard(String cardNumber) {
        return CardGeneratorService.isLuhn(cardNumber);
    }

}
