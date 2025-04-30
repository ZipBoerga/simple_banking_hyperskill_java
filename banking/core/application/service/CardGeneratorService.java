package banking.core.application.service;

import java.util.Random;

import banking.core.domain.model.Card;
import banking.core.domain.repository.CardRepo;

public class CardGeneratorService {
    private static final String PREFIX = "400000";
    private static final int NUMBER_LENGTH = 16;
    private static final int PIN_LENGTH = 4;
    private static final int ID_LENGTH = 7;
    private static final Random random = new Random();


    private final CardRepo cardRepo;

    public CardGeneratorService(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    public Card generateCard()  {
        int id = generateId();
        String number = generateNumber();
        String pin = generatePin();
        return new Card(id, number, pin);
    }

    public static boolean isLuhn(String cardNumber) {
        byte lastDigit = (byte) (cardNumber.charAt(cardNumber.length() - 1) - '0');
        int length = cardNumber.length();
        byte checksum = 0;
        for (int i = 0; i < length - 1; i++) {
            checksum = getChecksum(cardNumber, checksum, i);
        }
        return (checksum  + lastDigit) % 10 == 0;
    }

    private int generateId() {
        StringBuilder idBuilder = new StringBuilder();

        while (idBuilder.length() < ID_LENGTH) {
            idBuilder.append(random.nextInt(10));
        }

        int id = Integer.parseInt(idBuilder.toString());

        Card[] existingCards = cardRepo.getCards();
        for (Card card : existingCards) {
            if (card.getId() == id) {
                return generateId();
            }
        }

        return id;
    }

    // In very unlikely situations recursion to create another, non-repeating number may actually be used,
    // and the whole amount of cards present in DB can actually be put in heap twice... or more, and if there are lots
    // of data, it may create an overhead. The way to optimize it is to use for-loop instead of recursion.
    // but this is highly unlikely. So sticking with an elegant way. Still, it is a good consideration for a
    // bigger scale. Same for Id
    private String generateNumber() {
        StringBuilder cardNumberBuilder = new StringBuilder(PREFIX);

        while (cardNumberBuilder.length() < NUMBER_LENGTH - 1) {
            cardNumberBuilder.append(random.nextInt(10));
        }

        String incompleteNumber = cardNumberBuilder.toString();
        String number = incompleteNumber + String.valueOf(generateChecksumDigit(incompleteNumber));

        Card[] existingCards = cardRepo.getCards();
        for (Card card : existingCards) {
            if (card.getNumber().equals(number)) {
                return generateNumber();
            }
        }

        return number;
    }

    private static String generatePin() {
        Random rand = new Random();
        StringBuilder pin = new StringBuilder();

        while (pin.length() < PIN_LENGTH) {
            pin.append(rand.nextInt(10));
        }
        return pin.toString();
    }

    private static byte generateChecksumDigit(String incompleteNumber) {
        int length = incompleteNumber.length();
        byte checksum = 0;
        for (int i = 0; i < length; i++) {
            checksum = getChecksum(incompleteNumber, checksum, i);
        }
        byte checksumEndDigit = (byte) (checksum % 10);
        return (byte) (checksumEndDigit == 0 ? 0: 10 - checksumEndDigit);
    }

    private static byte getChecksum(String incompleteNumber, byte checksum, int i) {
        byte digit = (byte) (incompleteNumber.charAt(i) - '0');
        if (i % 2 == 0) {
            digit = (byte) (digit * 2);
            if (digit > 9) {
                digit = (byte) (digit - 9);
            }
        }
        checksum += digit;
        return checksum;
    }
}
