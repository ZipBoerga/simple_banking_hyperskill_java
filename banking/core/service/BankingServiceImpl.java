package banking.core.service;

import banking.domain.model.Card;
import banking.domain.repository.CardRepo;

public class BankingServiceImpl implements BankingService {

    private final CardRepo cardRepo;

    public BankingServiceImpl(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Override
    public Card createCard() {
        final Card card = CardGenerator.generateCard();
        cardRepo.saveCard(card);
        return card;
    }

    @Override
    public Card login(String cardNumber, String pin) {
        Card card = cardRepo.getCard(cardNumber);
        if (card != null && card.getCardPin().equals(pin)) {
            return card;
        } else {
            return null;
        }
    }
}
