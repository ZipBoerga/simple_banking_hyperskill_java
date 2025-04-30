package banking.core.application.service;

import banking.core.domain.model.Card;
import banking.core.domain.repository.CardRepo;

public class BankingServiceImpl implements BankingService {

    private final CardRepo cardRepo;
    private final CardGeneratorService cardGeneratorService;

    public BankingServiceImpl(CardRepo cardRepo, CardGeneratorService cardGeneratorService) {
        this.cardRepo = cardRepo;
        this.cardGeneratorService = cardGeneratorService;
    }

    @Override
    public Card createCard() {
        final Card card = cardGeneratorService.generateCard();
        cardRepo.saveCard(card);
        return card;
    }

    @Override
    public Card login(String cardNumber, String pin) {
        Card card = cardRepo.getCard(cardNumber);
        if (card != null && card.getPin().equals(pin)) {
            return card;
        } else {
            return null;
        }
    }
}
