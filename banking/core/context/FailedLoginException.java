package banking.core.context;

public class FailedLoginException extends Exception {
    public FailedLoginException() {
        super("Wrong card number or PIN!");
    }
}
