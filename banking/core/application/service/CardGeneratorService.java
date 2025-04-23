package banking.core.application.service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import banking.core.domain.model.Card;

public class CardGeneratorService {
    public static final String PREFIX = "400000";
    public static final int NUMBER_LENGTH = 16;
    public static final int PIN_LENGTH = 4;
    private static final Set<String> EXISTING_NUMBERS = new HashSet<>();

    public static Card generateCard()  {
        String number = generateNumber();
        String pin = generatePin();
        return new Card(number, pin);
    }

    private static String generateNumber() {
        Random rand = new Random();
        StringBuilder cardNumberBuilder = new StringBuilder(PREFIX);

        while (cardNumberBuilder.length() < NUMBER_LENGTH - 1) {
            cardNumberBuilder.append(rand.nextInt(10));
        }

        String incompleteNumber = cardNumberBuilder.toString();
        String number = incompleteNumber + String.valueOf(generateChecksumDigit(incompleteNumber));

        if (EXISTING_NUMBERS.contains(number)) {
            return generateNumber();
        }

        EXISTING_NUMBERS.add(number);
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
            byte digit = (byte) (incompleteNumber.charAt(i) - '0');
            if (i % 2 == 0) {
                digit = (byte) (digit * 2);
                if (digit > 9) {
                    digit = (byte) (digit - 9);
                }
            }
            checksum += digit;
        }
        byte checksumEndDigit = (byte) (checksum % 10);
        return (byte) (checksumEndDigit == 0 ? 0: 10 - checksumEndDigit);
    }
}
