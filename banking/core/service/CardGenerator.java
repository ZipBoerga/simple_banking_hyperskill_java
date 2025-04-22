package banking.core.service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import banking.domain.model.Card;

public class CardGenerator {
    public static final String PREFIX = "400000";
    public static final int NUMBER_LENGTH = 16;
    public static final int PIN_LENGTH = 4;
    private static final Set<String> EXISTING_NUMBERS = new HashSet<>();
    // TODO Luhn
    public static Card generateCard()  {
        String number = generateNumber();
        String pin = generatePin();
        return new Card(number, pin);
    }

    private static String generateNumber() {
        Random rand = new Random();
        StringBuilder card = new StringBuilder(PREFIX);

        while (card.length() < NUMBER_LENGTH) {
            card.append(rand.nextInt(10));
        }

        String number = card.toString();

        if (EXISTING_NUMBERS.contains(number)) {
            return generateNumber();
        }

        EXISTING_NUMBERS.add(number);
        return card.toString();
    }

    private static String generatePin() {
        Random rand = new Random();
        StringBuilder pin = new StringBuilder();

        while (pin.length() < PIN_LENGTH) {
            pin.append(rand.nextInt(10));
        }
        return pin.toString();
    }
}
