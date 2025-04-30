package banking.infrastructure.repository;

import banking.core.domain.model.Card;
import banking.core.domain.repository.CardRepo;

import java.util.HashMap;

public class FakeCardRepo implements CardRepo {
    private final HashMap<String, Card> cards = new HashMap<>();

    @Override
    public void saveCard(Card card) {
        cards.put(card.getNumber(), card);
    }

    @Override
    public Card getCard(String cardNumber) {
        return cards.get(cardNumber);
    }

    @Override
    public Card[] getCards() {
        return cards.values().toArray(new Card[0]);
    }
}
