package banking.core.domain.model;

public class Card {
    private final String cardNumber;
    private final String cardPin;
    private final int balance;

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardPin() {
        return cardPin;
    }

    public int getBalance() {
        return balance;
    }

    public Card(String cardNumber, String cardPin) {
        this.cardNumber = cardNumber;
        this.cardPin = cardPin;
        this.balance = 0;
    }
}
