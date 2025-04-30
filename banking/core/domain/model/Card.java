package banking.core.domain.model;

public class Card {
    private final int id;
    private final String number;
    private final String pin;
    private final int balance;

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public Card(int id, String number, String pin) {
        this(id, number, pin, 0);
    }

    public Card(int id, String number, String pin, int balance) {
        this.id = id;
        this.number = number;
        this.pin = pin;
        this.balance = balance;
    }


}
